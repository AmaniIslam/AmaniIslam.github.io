#Unit 2.3 - Rock, Paper, Scissors
#Amani Islam
#These codes import the random file so the I can use it later to pick a random number.
#Also, the counters for the scores are assigned.
import random
comp = 0
you = 0
#The loop for the game begins and continues while any of the scores are less than 4.
while you < 5 and comp < 5:
#These codes ask for the user's input and pick a random number.
    i = input("Please enter your choise 'r'ock, 'p'aper, or 's'cissors?")
    r = random.randint(0, 2)
#These codes give the conditions depending on what the user inputs.
    if i == "r":
#These codes give the conditions depending on which random number the computer chooses.
        if r == 0:
#These codes print the user's input, the computer's choice, adds and reapplies a value to the variables, and prints the scores.
            print("You chose: %s" % ("r"))
            print("The computer chose: %s" % ("r"))
            print("It's a tie!")
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
        elif r == 1:
            print("You chose: %s" % ("r"))
            print("The computer chose: %s" % ("p"))
            print("The compuer won!")
            comp +=1
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
        elif r == 2:
            print("You chose: %s" % ("r"))
            print("The computer chose: %s" % ("s"))
            print("You won!")
            you += 1
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
    elif i == "p":
        if r == 0:
            print("You chose: %s" % ("p"))
            print("The computer chose: %s" % ("r"))
            print("You won!")
            you += 1
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
        elif r == 1:
            print("You chose: %s" % ("p"))
            print("The computer chose: %s" % ("p"))
            print("It's a tie!")
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
        elif r == 2:
            print("You chose: %s" % ("p"))
            print("The computer chose: %s" % ("s"))
            print("The computer won!")
            comp +=1
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
    elif i == "s":
        if r == 0:
            print("You chose: %s" % ("s"))
            print("The computer chose: %s" % ("r"))
            print("The computer won!")
            comp +=1
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
        elif r == 1:
            print("You chose: %s" % ("s"))
            print("The computer chose: %s" % ("p"))
            print("You won!")
            you += 1
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
        elif r == 2:
            print("You chose: %s" % ("s"))
            print("The computer chose: %s" % ("s"))
            print("It's a tie!")
            print("Score:")
            print("\tComputer: %d" % (comp))
            print("\tYou: %d" % (you))
#This code doesn't print anything else except telling the user to input the correct values.
    else:
        print ("Please enter r, p, or s.")
#These codes print the winner based on which variable is  redifined as 5 first.
if comp == 5:
    print("%s has won!" % ("Computer"))
elif you == 5:
    print("%s have won!" % ("You"))
