#include <stdio.h>
#define MAX_LENGTH 100
#define MAX_LENGTH_TERM 4

int main(){
	
	int isNumber(char c);

	char input[MAX_LENGTH];

	printf("SPECIAL v0.1!\n");
	printf("Expression : ");

	scanf("%[^\n]s", input);

	printf("Input readback : %s\n", input);

	//Remove all spaces------------------------------------
	int validChar = 0;
	for (int i=0; i<MAX_LENGTH; i++){
		if (input[i] == '\0'){
			break;
		} else if (input[i] != ' '){
			validChar++;
		}
	}

	int length = validChar*2+1;
	char processedInput[length];

	for (int i=0; i<length; i+=2){
		processedInput[i] = '#';
	}

	int counter = 1;
	for (int i=0; i<length; i++){
		if (input[i] == '\0'){
			break;
		} else if (input[i] != ' '){
			processedInput[counter] = input[i];
			counter += 2;
		}
	}

	printf("Current expression : %s\n", processedInput);

	//Parse the expression----------------------------------
	//size 4 (MAX_LENGTH_TERM)
	for (int i=6; i<length; i++){
		if (processedInput[i-6] == 'c'){
			if (processedInput[i-4] == 'o'){
				if (processedInput[i-2] == 's'){
					if (processedInput[i] == 'h'){
						processedInput[i-5] = '@';
						processedInput[i-3] = '@';
						processedInput[i-1] = '@';
					}
				}
			}
		}
		if (processedInput[i-6] == 's'){
			if (processedInput[i-4] == 'i'){
				if (processedInput[i-2] == 'n'){
					if (processedInput[i] == 'h'){
						processedInput[i-5] = '@';
						processedInput[i-3] = '@';
						processedInput[i-1] = '@';
					}
				}
			}
		}
		if (processedInput[i-6] == 't'){
			if (processedInput[i-4] == 'a'){
				if (processedInput[i-2] == 'n'){
					if (processedInput[i] == 'h'){
						processedInput[i-5] = '@';
						processedInput[i-3] = '@';
						processedInput[i-1] = '@';
					}
				}
			}
		}
	}
	//size 3
	for (int i=4; i<length; i++){
		if (processedInput[i-4] == 'c'){
			if (processedInput[i-2] == 'o'){
				if (processedInput[i] == 's'){
					processedInput[i-3] = '@';
					processedInput[i-1] = '@';
				}
			}
		}
		if (processedInput[i-4] == 'e'){
			if (processedInput[i-2] == 'x'){
				if (processedInput[i] == 'p'){
					processedInput[i-2] = '^';
					processedInput[i-1] = '@';
					processedInput[i] = '@';
				}
			}
		}
		if (processedInput[i-4] == 'l'){
			if (processedInput[i-2] == 'o'){
				if (processedInput[i] == 'g'){
					processedInput[i-3] = '@';
					processedInput[i-1] = '@';
				}
			}
		}
		if (processedInput[i-4] == 's'){
			if (processedInput[i-2] == 'i'){
				if (processedInput[i] == 'n'){
					processedInput[i-3] = '@';
					processedInput[i-1] = '@';
				}
			}
		}
		if (processedInput[i-4] == 't'){
			if (processedInput[i-2] == 'a'){
				if (processedInput[i] == 'n'){
					processedInput[i-3] = '@';
					processedInput[i-1] = '@';
				}
			}
		}
	}
	//size 2
	for (int i=2; i<length; i++){
		if (isNumber(processedInput[i-2])){
			if (isNumber(processedInput[i])){
				processedInput[i-1] = '@';
			}
		}
	}

	printf("Current expression : %s\n", processedInput);

	//cleanup---------------------------------------------
	counter = 0;

	for (int i=0; i<length; i++){
		if (processedInput[i] == '#'){
			counter++;
		}
	}

	int tempLength = counter - 1;

	char parsedInput[tempLength][MAX_LENGTH_TERM];

	for (int i=0; i<tempLength; i++){
		for (int j=0; j<MAX_LENGTH_TERM; j++){
			parsedInput[i][j] = '\0';
		}
	}

	counter = -1;
	int tempCounter = 0;

	for (int i=0; i<length; i++){
		if (processedInput[i] == '#'){
			counter++;
			tempCounter = 0;
		} else if (processedInput[i] != '@'){
			parsedInput[counter][tempCounter++] = processedInput[i];
		}
	}

	length = tempLength;

	/*
	printf("Current expression : \n");

	for (int i=0; i<length; i++){
		printf("%s\n", parsedInput[i]);
	}
	return 0;
	*/
	//dealing with unary negative sign----------------------
	if (parsedInput[0][0] = '-'){
		parsedInput[0][1] = '1';
	}
	for (int i=1; i<length; i++){
		if (parsedInput[i][0] == '-'){
			if (!isNumber(parsedInput[i-1][0])){
				parsedInput[i][0] = '1';
			}
		}
	}

	printf("Current expression : \n");

	for (int i=0; i<length; i++){
		printf("%s\n", parsedInput[i]);
	}
	return 0;

}

int isNumber(char c){
	if (c >= 48 && c <= 57){
		return 1;
	} else {
		return 0;
	}
}