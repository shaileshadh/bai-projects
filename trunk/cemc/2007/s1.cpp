
#include <cstdio>

int main(){
	FILE *in = fopen("s1.in", "r");
	FILE *out = fopen("s1.out", "w");

	int iters;
	fscanf(in, "%d", &iters);

	for(int i=0; i<iters; i++){
		int year, month, day;
		fscanf(in, "%d %d %d", &year, &month, &day);
		int eligable;

		if(year < 1989)
			eligable = 1;
		else if(year == 1989){
			if(month < 2)
				eligable = 1;
			else if(month == 2){
				if(day <= 27)
					eligable = 1;
				else eligable = 0;
			}
			else eligable = 0;
		}
		else eligable = 0;
		
		if(eligable)
			fprintf(out, "Yes\n");
		else fprintf(out, "No\n");
	}

	fclose(in);
	fclose(out);
	return 0;
}
