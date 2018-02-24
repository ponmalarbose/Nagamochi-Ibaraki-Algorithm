#include<stdio.h>
#include <iostream.h>
#include<math.h> 
#include<conio.h>
#include<stdlib.h>
using namespace std; 
void graphgen(int,int);
void graphgenerator(int, int);
int nagamochi (int);
int cedges(int , int); 
int adj[100][100]; 
int nagibamatrix[100][100]; 
int cpy[100][100]; 
int edgeexist[100][100]; 
int d[100];
//Draw a graph
void graphgen(int i, int j) 
{
FILE* graph; graph=fopen("graph.dot","w"); 
fprintf(graph,"digraph G {\n"); 
fprintf(graph,"%d -> %d [dir=none];\n",i,j); 
fprintf(graph,"}"); 
fclose(graph); 
}
//Algorithm
int nagamochi(int tot) 
{
int max = 0,n=0,l,u,t,lamda=0,start, choosenode, prev1, prev2; 
int madj[100]; 
while (tot > 2 ) 
{ 
start = 0; 
for (l=0;l l<tot;l++)
madj[l]=0; 
choosenode = rand()%tot; //choosing a random node 
madj[start] = choosenode;
for (l=1;l<tot;l++) 
{ 
max = 0; 
for (u=0;u<tot; u++) 
{ 
if (adj[choosenode][u] > max) 
{ 
max = adj[choosenode][u]; 
n= u; 
} 
} 
madj[++start]= n; 
for (u=0;u<tot;u++) 
{ 
adj[choosenode][u] += adj[n][u]; 
adj[u][choosenode] += adj[u][n]; 
adj[choosenode][choosenode] = 0; 
}
for (u=0;u<tot;u++) 
{ 
adj[n][u]=adj[u][n]=0; 
}
} 
if ((!lamda) || (lamda >= d[madj[tot-1]])) 
{ 
lamda = d[madj[tot-1]]; 
} 
prev1 = madj[tot-1]; 
prev2 = madj[tot-2]; 
for (l=0; l < tot; l++) 
{ 
nagibamatrix[prev2][l] += nagibamatrix[prev1][l]; 
nagibamatrix[l][prev2] += nagibamatrix[l][prev1]; 
nagibamatrix[prev2][prev2] = 0; 
nagibamatrix[prev1][l] = nagibamatrix[tot-1][l]; 
nagibamatrix[l][prev1] = nagibamatrix[l][tot-1]; 
} 
tot = tot-1; 
for (l=0; l<tot; l++) 
for (t=0; t <tot; t++)
adj[l][t]=nagibamatrix[l][t]; 
} 
return (lamda); 
} 
//_______________FUNCTION TO CREATE GRAPH___________________ 
void graphgenerator(int n, int maxedges) 
{ 
int i,j,p,q,x; 
int cnt = maxedges; 
while(cnt>0) 
{ 
for(i=0;i<n;i++) 
{ 
for(j=0;j<n;j++) 
{ 
if ((i == j)) 
{ 
adj[i][j] = adj[j][i] = 0; 
edgeexist[i][j] = edgeexist[j][i] = 1; 
} 
else 
{ 
if ((edgeexist[i][j] == 0) && (edgeexist[j][i] == 0)) 
{ 
if ((adj[i][j] == 0) && (adj[j][i] == 0)) 
{ 
x = (rand()%1); 
if (x = 1) 
{ 
adj[i][j] = adj[j][i] = 1; 
cnt--; 
d[i]=d[i]+1; 
d[j]=d[j]+1; 
edgeexist[i][j] = edgeexist[j][i] = 1; 
graphgen(i,j); 
if (cnt <= 0) 
break; 
} 
else 
{ adj[i][j] = adj[j][i] = 0; 
edgeexist[i][j] = edgeexist[j][i] = 1; 
} 
} 
} 
} 
} 
if (cnt <= 0) 
break; 
} 
if (cnt) 
{ 
for (p=0;p<n;p++) 
for (q=0;q<n;q++) 
edgeexist[p][q]= 0; 
} 
} 
} 
//_____FUNCTION TO COMPUTE CRITICAL EDGES_______ 
int cedges(int n, int l){ 
int cnt = 0,i,j; 
int criticalarray[100]; 
for(i=1;i<n;i++) 
{ 
for(j=1;j<n;j++) 
{ 
if(adj[i][j]==1) 
{adj[i][j]=0; 
adj[j][i]=0; 
criticalarray[i]=d[i]; 
criticalarray[j]=d[j]; 
adj[i][j]=1; 
adj[j][i]=1; 
} 
} 
} 
for(i=0;i<n;i++)
{ 
if(nagamochi(d[i])>l) 
{ 
cnt++; 
} 
} 
return cnt; 
} 
int main() 
{ 
int di,q,lr,h,l,criticaledges,n,p,m,b; 
cout<<"\n"<<"\t"<<"\t"<<"*NAGAMOCHI-IBARAKI ALGORITHM*"<<"\n"; 
cout<<"\n"<<"******ENTER THE NUMBER OF NODES*******:"<<"\n"; 
cin>>n; 
cout<<"\n"<<"******ENTER THE NUMBER OF EDGES********:"<<"\n"; 
cin>>m; 
l = 0; 
di = 2*m/n; 
for (lr=0;lr<n;lr++) 
{ 
d[lr]=0; 
} 
for (p=0;p<n;p++) 
for (q=0;q<n;q++) 
edgeexist[p][q]= 0; 
graphgenerator(n,m); 
for (b=0;b<n;b++) 
{ 
for (h=0;h<n;h++) 
{ 
nagibamatrix[b][h]=adj[b][h]; 
cpy[b][h]=adj[b][h]; 
} 
} 
l = nagamochi(n); 
criticaledges = cedges(n,l); 
cout<<"\n "<<"No. OF EDGES"<<"\t"<<" DEGREE"<<" \t "<<"LAMBDA "<<"\t"<<" CRITICAL EDGES"<<" \n"; 
cout<<"\n "<<m<<" \t\t "<<di<<"\t\t "<<l<<"\t\t "<<criticaledges; 
getch();
return 0; 
}

