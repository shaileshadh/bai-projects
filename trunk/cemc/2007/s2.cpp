
#include <cstdio>
#include <cstdlib>

int cmpint(const void *a, const void *b){
	return *(int*)a - *(int*)b;
}

int cmpiarray(const void *a, const void *b){
	int pa=1, pb=1;
	int *x=(int*)a, *y=(int*)b;
	for(int i=0; i<3; i++){
		pa*=x[i];
		pb*=y[i];
	}
	return pa-pb;
}

int main(){
	FILE *in = fopen("s2.in", "r");
	FILE *out = fopen("s2.out", "w");

	int numboxes;
	fscanf(in, "%d", &numboxes);
	int boxes[numboxes][3];

	for(int i=0; i<numboxes; i++){
		fscanf(in, "%d %d %d", &boxes[i][0], &boxes[i][1], &boxes[i][2]);
		qsort(boxes[i], 3, 4, cmpint);
	}

	qsort(boxes, numboxes, 12, cmpiarray);

	int numobjs;
	fscanf(in, "%d", &numobjs);

	for(int i=0; i<numobjs; i++){
		int a[3], min=0;
		fscanf(in, "%d %d %d", &a[0], &a[1], &a[2]);
		qsort(a, 3, 4, cmpint);
		for(int k=0; k<numboxes; k++){
			if(a[0] <= boxes[k][0] && a[1] <= boxes[k][1] && a[2] <= boxes[k][2]){
				min = boxes[k][0] * boxes[k][1] * boxes[k][2];
				break;
			}
		}
		if(min>0)
			fprintf(out, "%d\n", min);
		else fprintf(out, "Item does not fit.\n");
	}

	fclose(in);
	fclose(out);
	return 0;
}
