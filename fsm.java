
import java.util.*;
import java.util.regex.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//alphatbet = abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,~!@$#%^&-+{}


public class FSM {
	
	//Adam driving
	public static boolean validAlpha(String x){
		
		 Pattern p = Pattern.compile("[a-zA-Z0-9\\.,~!@\\$#%\\^&\\-\\+\\{}]*");
		 Matcher m = p.matcher(x);
		 boolean b = m.matches();

		 return b;
	}
	
	//Adam
	public static void invalidChars(String x, String inOut, String fsm, boolean isWarnings){
		//System.out.println(x);
		boolean breakIt = false;
		String chars = "";
		Pattern p = Pattern.compile("[a-zA-Z0-9\\.,~!@\\$#%\\^&\\-\\+\\{}]*");
		for(int i = 0; i < x.length(); i++){
			chars = chars + x.charAt(i);
			Matcher m = p.matcher(chars);
			boolean b = m.matches();
			if(isWarnings && !b && (x.charAt(i) != '\n')){
				System.out.println("FSM WARNING: " + fsm + ": UNSUPPORTED ALPHABET FOR " + x);
				
			}
			if(inOut.equalsIgnoreCase("MACHINE_TYPE")){
				System.out.println("FSM FILE ERROR: "+ fsm + ": "+ inOut + " HAS INVALID VALUE '" + x +"'");
				
			}
			if(!b){
				System.out.println("FSM FILE ERROR: "+ fsm + ": "+ inOut + " HAS INVALID VALUE '" + x.charAt(i) +"'");
				
			}
			chars = "";
		}
	}
	
	//Adam
	public static String removeWhite(String x) throws IOException{
		
		FileInputStream inStream = new FileInputStream(x);
		String wholeFile = "";
		int lineCount = 1;
		while(true){
			
			char toTest = (char)inStream.read();
			
			if(toTest == (char)-1){
				break;
			}
			if(toTest == '\n'){
				lineCount++;
			}
			//removing whitespaces
			if(toTest != ' ' && toTest != '\t'){
				wholeFile = wholeFile + toTest;
			}
			//System.out.println(toTest);
		}
		return wholeFile;
	}


	//Adam
	public static List<String> inputName(String x) throws IOException{
		String what = removeWhite(x);
		what = what + ' ';
		List<String> lines = new LinkedList<String>();
		//FileInputStream inStream = new FileInputStream(x);
		
		//char test1 = (char)inStream.read();
		String lineForList = "";
		for(int i = 0; i <= what.length(); i++){
			if(what.charAt(i) == ' '){
				break;
			}
			if(what.charAt(i) != '\n'){
				lineForList = lineForList + what.charAt(i);
			}
			if(what.charAt(i) == '\n' || i + 2 == what.length()){
				if(lineForList.length() != 0)				
					lineForList = lineForList.substring(0, lineForList.indexOf(':'));
					lines.add(lineForList);
					lineForList = "";
				}
				else{	
				}
			}
		}
		return lines;
	}
	//Adam
	public static List<String> getInputs(String x) throws IOException{
		
		String what = removeWhite(x);
		what = what + ' ';

		List<String> lines = new LinkedList<String>();
		//FileInputStream inStream = new FileInputStream(x);
		
		//char test1 = (char)inStream.read();
		String lineForList = "";
		for(int i = 0; i <= what.length(); i++){
			if(what.charAt(i) == ' '){
				break;
			}
			if(what.charAt(i) != '\n'){
				lineForList = lineForList + what.charAt(i);
			}

			if(what.charAt(i) == '\n' || i + 2 == what.length()){
				if(lineForList.length() != 0){
					
					lineForList = lineForList.substring(lineForList.indexOf(':') + 1, lineForList.length());
					lines.add(lineForList);
					lineForList = "";
				}
				else{	
				}
			}
		}
		
		return lines;
	}
	
	//Adam
	public static String getInputName(String x, String input, int index){
		int delimiter = 0;
		delimiter = x.indexOf(':');
		String name = "";
		if(delimiter == 0){
			System.out.println("INPUT FILE ERROR: " + input + ": MISSING NAME ON LINE: " + (index + 1));
			
		}
		name = x.substring(0, delimiter);
		return name;
	}
	
	//Adam
	public static String getInputLine(String x, String input, int index){
		int delimiter = 0;
		int count = 0;
		delimiter = x.indexOf(':');
		for (int i = 0; i < x.length(); i++){
			if(x.charAt(i) == ':')
			{
				count++;
			}
		}
		String name = "";
		System.out.println("COUNT = " + count);
		if (count > 0){
			System.out.println("INPUT FILE ERROR: " + input + ": MULTIPLE STRINGS ON LINE " + index);
			
		}
		if(delimiter+1 == x.length()){
			System.out.println("INPUT FILE ERROR: " + input + ": MISSING INPUT STRING ON LINE " + (index + 1));
			
		}
		name = x.substring(delimiter + 1, x.length());
		return name;
	}
	
	//Adam
	//get the alphabet, taking into account the shortcut characters
	public static String getAlpha(String x){
		if(x.equals("|u")){
			return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		else if(x.equals("|l")){
			return "abcdefghijklmnopqrstuvwxyz";
		}
		else if(x.equals("|a")){
			return getAlpha("|l").concat(getAlpha("|u"));
		}
		else if(x.equals("|d")){
			return "0123456789";
		}
		else if(x.equals("|n")){
			return "123456789";
		}
		else if(x.equals("|s")){
			return ".,~!@$#%^&-+{}";
		}
		else{
			return x;
		}
	}
	
	//Adam
	//funtion to imbed commas into the alphabet strings if needed
	public static String getAlphaCommas(String x){
		if(x.equals("|u")){
			return "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		}
		else if(x.equals("|l")){
			return "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
		}
		else if(x.equals("|a")){
			return getAlpha("|l").concat(getAlpha("|u"));
		}
		else if(x.equals("|d")){
			return "0,1,2,3,4,5,6,7,8,9";
		}
		else if(x.equals("|n")){
			return "1,2,3,4,5,6,7,8,9";
		}
		else if(x.equals("|s")){
			return ".,,,~,!,@,$,#,%,^,&,-,+,{,}";
		}
		else{
			return x;
		}
	}
	
	//Adam
	//not used
	//basically was going to blow up the piped input/output values
	public static String blowUp(String x){
		//System.out.println("Line X = " + x);
		String fantastic = "";
		String builder = "";
		for(int i = 0; i < x.length(); i++){
			if(x.charAt(i) == '|'){
				builder+=x.charAt(i);
				builder+=x.charAt(i+1);
				builder = getAlphaCommas(builder);
				fantastic = fantastic.concat(builder);
				builder = "";
				i++;
			}
			else{
				fantastic+=x.charAt(i);
			}
		}
		return fantastic;
	}
	
	//Adam
	//grab a line number from the machine.fsm file
	public static int getLine(String wholeFile, String toSearch){
		String temp = "";
		char temp2;
		int line = 1;
		for (int i = 0; i < wholeFile.length(); i++){
			temp2 = wholeFile.charAt(i);
			temp = temp + temp2;
			if (wholeFile.charAt(i) == '\n'){
				line++;
			}	
			if (temp.endsWith(toSearch)){
				break;
			}	
		}
		return line;
	}
	
	//Adam
	//checks to see if the state is an accepting state
	public static boolean isAccept(String x){
		String stateName = "";
		stateName = x.substring(0, x.indexOf(':'));
		if(stateName.contains("$") || stateName.contains("$!") || stateName.contains("!$")){
			return true;
		}
		else{
			return false;
		}
	}
	//Adam
	//IT'S A TRAP! or is it?
	public static boolean isTrap(String x){
		String stateName = "";
		stateName = x.substring(0, x.indexOf(':'));
		if(stateName.contains("!") || stateName.contains("$!") || stateName.contains("!$")){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	//Adam
	//building up the alphabet
	public static String buildAlpha(String x){
		String alphabet = "";
		char charToCheck;
		String shortcut;
		
		for(int i = 0; i < x.length(); i++){
			
			charToCheck = x.charAt(i);
			Pattern p = Pattern.compile("[|]+[uladns]");
			
			if(charToCheck == '|'){
				shortcut = "|" + x.charAt(i+1);
				Matcher m = p.matcher(shortcut);
				boolean b = m.matches();
				if (!b){
					return "Invalid shortcut";
				}
				else{
					String test = getAlpha(shortcut);
					alphabet = alphabet.concat(getAlpha(shortcut));
					i++;
				}
			}
			else{
				alphabet = alphabet + charToCheck;
			}
		}	
		return alphabet;
	}
	
	//Adam
	//total length of the file -- could be handy
	public static int fileLength(String inFile){
		int length = inFile.length();
		return length;
	}
	
	//Adam
	//finding the start state
	public static String startState(String wholeFile, int startIndex, String fsmName){
		char build = wholeFile.charAt(startIndex);
		String startState = "";

		while(true){
			if(build == '\n' || startIndex == fileLength(wholeFile)-1){
				break;
			}
			startState = startState + build;
			startIndex++;

			build = wholeFile.charAt(startIndex);
		}
		invalidChars(startState, "START_STATE", fsmName, true);
		return startState;
	}
	
	//Adam
	//work-horse method, parses the machine.fsm file and gets all the goodies
	public static ArrayList<States> parse(String inFile, String fsmName, boolean verbose, boolean warnings, boolean unspecified) throws IOException{
		String name;
		FileInputStream inStream = new FileInputStream(inFile);
		ArrayList<States> returnVal = new ArrayList<States>();
		int lineCount = 1;
		String wholeFile = "";
		//machinename missing?

		//counting lines, removing whitespaces
		while(true){
			char toTest = (char)inStream.read();
			if(toTest == (char)-1){
				break;
			}
			if(toTest == '\n'){
				lineCount++;
			}
			//removing whitespaces
			if(toTest != ' '){
				wholeFile = wholeFile + toTest;
			}
		}
		wholeFile = wholeFile + ' ';
		
		String rlRemove = "";
		for(int r = 0; r < wholeFile.length(); r++){
			char blah = wholeFile.charAt(r);
			if(blah != '\n'){
				rlRemove = rlRemove + blah;
			}

		}
		//doesn't have an FSM name? ERROR!
		if(rlRemove.startsWith("INPUT_ALPHABET") || rlRemove.startsWith("OUTPUT_ALPHABET") || rlRemove.startsWith("MACHINE_TYPE") || rlRemove.startsWith("STARTING_STATE")){
			System.out.println("FSM FILE ERROR: " + fsmName + ": FSM NAME IS MISSING");
			System.exit(0);
		}
		//doesn't have an machine type? ERROR!
		if (!wholeFile.contains("MACHINE_TYPE")){
			System.out.println("FSM FILE ERROR: " + fsmName + " : MACHINE_TYPE IS MISSING");
			System.exit(0);
		}
		//doesn't have a starting state? ERROR!
		if (!wholeFile.contains("STARTING_STATE")){
			System.out.println("FSM FILE ERROR: " + fsmName + " : STARTING_STATE IS MISSING");
			System.exit(0);
		}
		
		//getting the machine type from input file
		//find line that the machine is on, cut it out of the file, check for match
		
		//now to get the type of machine, this is stored in a boolean to be sent to the States class
		boolean machine = false;
		if(wholeFile.contains("MACHINE_TYPE")){
			int machineIndex = wholeFile.indexOf("MACHINE_TYPE");
			String machineType = "";
			int indexOfNew = 0;

			for(int i = machineIndex; wholeFile.charAt(i) != '\n';i++){
				indexOfNew++;
			}
			String machineLine = wholeFile.substring(machineIndex, machineIndex + indexOfNew);


			String type = machineLine.substring(machineLine.indexOf(':') + 1, machineLine.length());


			if (type.equalsIgnoreCase("mealy") || type.equalsIgnoreCase("moore")){
				
				if(type.equalsIgnoreCase("mealy")){
					machine = true;
				}
				else{
					machine = false;
				}
				//System.out.println("machine type = " + machine );
			}
			else{
				invalidChars(machineType, "MACHINE_TYPE", fsmName, true);
				//System.out.println("FSM ERROR: " + fsmName + " INVALID MACHINE TYPE SPECIFIED");
				System.exit(0);
			}
		}
		else{
			System.out.println("FSM ERROR: " + fsmName + " INVALID MACHINE TYPE SPECIFIED");
			System.exit(0);
		}
		
		//getting input line from the file, cut it out, and check it.
		String pipe = "|";
		//getting input alphabet from input file
		String alphabetIn = "";
		String alphabetOut = "";
		if(wholeFile.contains("INPUT_ALPHABET")){
	
			int inputIndex = wholeFile.indexOf("INPUT_ALPHABET");
			int indexOfNew = 0;

			for(int i = inputIndex; wholeFile.charAt(i) != '\n';i++){
				indexOfNew++;
			}
			String inputLine = wholeFile.substring(inputIndex, inputIndex + indexOfNew);
			String testInputLine = "";
			for(int z = 0; z < inputLine.length(); z++){
				if(inputLine.charAt(z) == '|'){
					testInputLine = testInputLine + getAlpha(pipe + inputLine.charAt(z+1));
					z++;
				}
				else{
					testInputLine = testInputLine + inputLine.charAt(z);
				}
			}
			int whereiscolon = 0;
			for(int g = 0; g < testInputLine.length(); g++){
				if(testInputLine.charAt(g) == ':' ){
					whereiscolon = g;
				}
			}
			String somuchwastedtime = testInputLine.substring(whereiscolon+1, testInputLine.length());
			System.out.println("SOMUCHWASTED TIME = " + somuchwastedtime);
			invalidChars(somuchwastedtime, "INPUT_ALPHABET", fsmName, warnings);
			
			String inputFinal = getAlpha(inputLine.substring(inputLine.indexOf(':') + 1, inputLine.length()));
			for(int b = 0; b < inputFinal.length(); b++){
				if(inputFinal.charAt(b) == '|'){
					alphabetIn = alphabetIn + getAlpha(pipe + inputFinal.charAt(b+1));
					b++;
				}
				else{
					alphabetIn = alphabetIn + inputFinal.charAt(b);
				}
			}
			
		}
		else{
			alphabetIn = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9,.,,,~,!,@,$,#,%,^,&,-,+,{,}";
		}
		//make sure is valid and whatnot
		boolean isValidAlpha = validAlpha(alphabetIn);
		invalidChars(alphabetIn, "INPUT_ALPHABET", fsmName, true);
		
		String inputAlpha = buildAlpha(alphabetIn);
		//System.out.println("Input Alphabet = " + inputAlpha);
				
		//now we have input alphabet, machine type, machine name
		//still need --- state transitions, output alphabet
		
		//getting output alphabet from input file
		if(wholeFile.contains("OUTPUT_ALPHABET")){
	
			int inputIndex = wholeFile.indexOf("OUTPUT_ALPHABET");
			int indexOfNew = 0;

			for(int i = inputIndex; wholeFile.charAt(i) != '\n';i++){
				indexOfNew++;
			}
			;
			String inputLine = wholeFile.substring(inputIndex, inputIndex + indexOfNew);

			String outputFinal = getAlpha(inputLine.substring(inputLine.indexOf(':') + 1, inputLine.length()));
			for(int b = 0; b < outputFinal.length(); b++){
				if(outputFinal.charAt(b) == '|'){
					alphabetOut = alphabetOut + getAlpha(pipe + outputFinal.charAt(b+1));
					b++;
				}
				else{
					alphabetOut = alphabetOut + outputFinal.charAt(b);
				}
			}
			
		}
		else{
			alphabetOut = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9,.,,,~,!,@,$,#,%,^,&,-,+,{,}";
		}
		
		boolean isValidAlphaOut = validAlpha(alphabetOut);
		invalidChars(alphabetOut, "OUTPUT_ALPHABET", fsmName, true);
		if(!isValidAlphaOut){
			System.out.println("FSM ERROR: " + fsmName + " OUTPUT_ALPHABET HAS INVALID VALUE");
//			System.exit(0);
		}
		String outputAlpha = buildAlpha(alphabetOut);
		//System.out.println("Output Alphabet = " + outputAlpha);
		
		
		
		//still Adam
		int startIndex = wholeFile.indexOf("STARTING_STATE");
		String startLine = "";
		int endofStart = 0;
		for (int a = startIndex; wholeFile.charAt(a) != '\n';a++){
			endofStart++;
		}

		startLine = wholeFile.substring(startIndex, startIndex + endofStart);
		String sState = startLine.substring((startLine.indexOf(':')+1), startLine.length());
		//now we need to get the individual states...

		int statesIndex = startIndex;
		char current = wholeFile.charAt(statesIndex);
		while(true){
			if(wholeFile.charAt(statesIndex) == '\n'){
				break;
			}
			statesIndex++;
		}

		//getting just the lines of the file dealing with states...
		String subWhole = wholeFile.substring(statesIndex, fileLength(wholeFile)) + ' ';
		int filelength = fileLength(subWhole);

		
		//get an individual line of states
		//this is where we should send
		int index = 0;

		char toTest = subWhole.charAt(index);
		String stateLine = "";
		while(toTest == '\n'){
			index++;
			toTest = subWhole.charAt(index);
		}
		
		while(index < subWhole.length()+10){
			while(true){
				if (subWhole.charAt(index) == '\n' || index == fileLength(subWhole)-1){
					break;
				}
				stateLine = stateLine + subWhole.charAt(index);
				
				index++;				
			}
			//various System.outs to make sure everything is correct
			if(stateLine != ""){
				int lineNum = getLine(wholeFile, stateLine);
				boolean isAccept = isAccept(stateLine);
				boolean isTrap = isTrap(stateLine);
				
				int placeHolder = stateLine.indexOf(':');
				if(placeHolder == -1){
					System.out.println("FSM FILE ERROR: " + fsmName + " INVALID STATE TRANSITION");
				}
				else{
					name = stateLine.substring(0, placeHolder); 
					if(name.equals(sState))
						returnVal.add(0, new States(stateLine, lineNum, isTrap, isAccept, machine, verbose, warnings, unspecified, inputAlpha, outputAlpha,fsmName));
					else
						returnVal.add(new States(stateLine, lineNum, isTrap, isAccept, machine, verbose, warnings, unspecified, inputAlpha, outputAlpha,fsmName));
				}
			}
			stateLine = "";
			if(index == fileLength(subWhole)-1)
			{
				break;
			}
			index++;
		}

		//Neil driving
		Set<String> allNextStates = new HashSet<String>();
		boolean temp = false; 
		boolean used = true;
		
		//Makes sure there is an Accepting state and get all states pointed to
		for(int x = 0; x < returnVal.size(); x++)
		{
			allNextStates.addAll(returnVal.get(x).getAllNextSTates());
			
			if(returnVal.get(x).hasAccepting())
			{
				temp = true;
				x = returnVal.size();
			}
		}
		
		//Checks to see if any state has nothing pointing to it
		for(int x = 1; x < returnVal.size(); x++)
		{
			
			if(!allNextStates.contains(returnVal.get(x).getName()))
			{
				used = false;
				x = returnVal.size();
			}
		}
		
		//System.out.println(allNextStates.toString());
		
		//Displays the warnings for Missing Accepting state and Unconnected components
		if(warnings)
		{
			if(!temp)
			{
				System.out.println("FSM WARNING: " + fsmName + " MISSING ACCEPTING STATE.");
			}
			
			if(!used)
			{
				System.out.println("FSM WARNING: " + fsmName + " UNCONNECTED COMPONENTS.");
			}
		}
		
		//Sends into a unspecified state if need be
		if(unspecified){
			System.out.println("UNLABELDDZXXX");
			//returnVal.add(new States("UNLABELEDXXXXX:", 0, true, false, machine, verbose, warnings, unspecified, inputAlpha, outputAlpha, fsmName));
		}
		
		return returnVal;
		//now we need to get the starting state, and the other states, must have a final state? check on this
	}
	
	//Adam
	public static void main(String[] args) throws IOException {
		//adam is driving
		boolean isVerbose = false;
		boolean isWarnings = false;
		boolean isUnspecified = false;
		String fsmName = "";


		List<String> arguements = new LinkedList<String>();
		List<String> machines = new LinkedList<String>();
		String input = "";
		for (String s: args) {
			arguements.add(s);
		}
		
		int listSize = arguements.size();

		
		int i = 0;
		while(i < listSize){
			//getting machines
			if(arguements.get(i).equalsIgnoreCase("--fsm")){
				//System.out.println("FSM INIT FOUND");
				i++;
				
				while(!arguements.get(i).startsWith("--")){
					machines.add(arguements.get(i));
					//System.out.println(arguements.get(i));
					i++;
					if(i == listSize){
						break;
					}
				}
			}
			if(i == listSize){
				break;
			}
			//getting input checking for tags 
			if(arguements.get(i).equalsIgnoreCase("--input")){
				//System.out.println("INPUT INIT FOUND");
				i++;
				input = arguements.get(i);
			}
			if(arguements.get(i).equalsIgnoreCase("--verbose")){
				//System.out.println("VERBOSE INIT FOUND");
				isVerbose = true;
			}
			if(arguements.get(i).equalsIgnoreCase("--warnings")){
				//System.out.println("WARNINGS INIT FOUND");
				isWarnings = true;
			}
			if(arguements.get(i).equalsIgnoreCase("--unspecified-transitions-trap")){
				//System.out.println("UNSPECIFIED INIT FOUND");
				isUnspecified = true;
			}
			i++;
		}
		
		List<String> inputList = getInputs(input);
		List<String> inputNames = inputName(input);

		int numInputs = inputList.size();
		int numMachines = machines.size();
		ArrayList<States> test = new ArrayList<States>();
		ArrayList<ArrayList<States>> allStates = new ArrayList<ArrayList<States>>();
		allStates.add(test);
		//adam still driving
		//Now were are going to go through each machine and run each input from the input file on it
		//using the array lists above and a double-for loop etc...
		for(int count = 0; count < numMachines; count++){
			test = parse(machines.get(count), machines.get(count), isVerbose, isWarnings, isUnspecified);
			System.out.println();
			System.out.println("<<<<<<"+ machines.get(count) + ">>>>>>");
			for(int countInputs = 0; countInputs < numInputs; countInputs++){
				
				String inputString = inputList.get(countInputs);
				
				int v = 0;
				if(test.get(v).isMealy()){
					System.out.print(machines.get(count) + " : " + inputString + " / ");
				}
				for (int z = 0; z < inputString.length(); z++){
					//System.out.println("BORAT: " + test.get(v).getAlphabet().indexOf(inputString.charAt(z)));
					if(test.get(v).getAlphabet().indexOf(inputString.charAt(z)) == -1){
						System.out.println("FSM FILE ERROR: " + machines.get(count) + " INVALID OUTPUT SYMBOL ON LINE " + (countInputs+1));
						break;
					}
					String nextState = test.get(v).nextState(inputString.charAt(z));

					if(isVerbose){
						System.out.println((z+1) +" : " + z + " " + test.get(v).getName() + " -- " + inputString.charAt(z) + " --> " + nextState);
					}
					if(nextState.equals(":")){
						System.out.println("ACCEPTED " + test.get(v).getAlphabet());
						z = inputString.length();
					}
					
					else{
						for(int p = 0; p < test.size(); p++){
							if(test.get(p).getName().equals(nextState)){;
								v = p;
								p = test.size();
							}
						}
					}
				}
				if(test.get(v).isMealy()){			
					System.out.println();
				}
				if(test.get(v).isFinal() && !test.get(v).isMealy()){
					System.out.println(machines.get(count) + " : ACCEPTED " + inputString);
				}
				else if(!test.get(v).isFinal() && !test.get(v).isMealy()){
					System.out.println(machines.get(count) + " : REJECTED " + inputString);
				}
			}
		}
	}
}


//Neil is driving
final class States {

	//Strings need to store data
	String name;
	String preConections;
	String nextStatesLine;
	String inputAlphabet;
	String outputAlphabet;
	String FSMname;
	
	//has a set of all next states
	Set<String> nextStateList = new HashSet<String>();
	String usedInputs = "";
	
	//Pattern for Rejex
	Pattern inAplha;
	Pattern outAplha;
	Pattern nameAlpha = Pattern.compile("a*b");
	
	//ADD THIS
	Pattern nameExp = Pattern.compile("[a-zA-Z0-9]*");
	Pattern allSymbols = Pattern.compile("[a-zA-Z0-9\\.,~!@\\$#%\\^&\\-\\+\\{}]*");
	Matcher validAlpha = allSymbols.matcher(" ");
	
	int line;
	
	//bolleans for needed things
	boolean isFinal = false;
	boolean isTrap = false;
	boolean linksSet = false;
	boolean isStart = false;
	boolean isMealy;
	boolean isVerbose;
	boolean isWarnings;
	boolean isUnspecified;
	
	//holds all the nextstates according to values
	HashMap<String, String> nextStates = new HashMap<String, String>();
	
	//ArrayList<String> removedStates;
	
	//Neil is driving
	public States(String origianlLine, int line1, boolean trap, boolean finalState, boolean mealy, 
			boolean verbose, boolean warnings, boolean unspecified, String inputAlph, String outputAlph, String FsmName)
	{
		
		System.out.println("INPUTALPH = " + inputAlph);
		preConections = origianlLine;
		line = line1;
		inputAlphabet = inputAlph;
		outputAlphabet = outputAlph;
		FSMname = FsmName;
		
		//Gets the index of the colon
		int placeHolder = origianlLine.indexOf(':');
		name = origianlLine.substring(0, placeHolder); 
		//adam driving to fix case where a state is ! and $
		
		//System.out.println("NAME " + name);
		
		//gets final state and trap state
		if(name.contains("$!") || name.contains("!$")){
			name = name.substring(0, name.length()-1);
		}
		if(name.endsWith("$") || name.endsWith("!")){
			name = name.substring(0, name.length()-1);
		}
		
		
		nextStatesLine = origianlLine.substring(1+placeHolder);
		
		isTrap = trap;
		isFinal = finalState;
		isMealy = mealy;
		isVerbose = verbose;
		isWarnings = warnings;
		isUnspecified = unspecified;
		
		//makes the hashmaps if it is not a trap state
		if(!isTrap){
			nextStates.clear();
			nextStates = newLinks(nextStatesLine);
		}
		
		//Outputs the warnings for transition state
		if(warnings)
		{
			for(int x = 0; x < inputAlphabet.length(); x++)
			{
				if(usedInputs.indexOf(inputAlphabet.charAt(x)) == -1)
				{
					System.out.println("FSM WARNING: " + FSMname + " INCOMPLETE TRANSITION TABLE FOR STATE " + name);
					x= inputAlphabet.length();
				}
			}
		}
		
		
	}
	
	//You can make this a boolean so that true if worked false is not
	//Neil is driving
	public HashMap<String, String> newLinks(String connections)
	{
		//needed data storage for adding the values to the hashmap
		int placeHolder = 0;
		int placeHolderTwo = 0;

		String newNextStateName;
		String inputs;
		String output = null; 
		String checker;

		placeHolder = connections.indexOf(':');
		//Check to make sure line is made properly
		if(placeHolder == -1 || placeHolder == 0)
		{
			System.out.println("FSM FILE ERROR: " + FSMname + " STATE HAS NO TRANSITIONS OR NAME LISTED AT LINE " + line);
			//System.exit(0);

		}


		while(placeHolder != -1)
		{
			//Gets the name of the 
			newNextStateName = connections.substring(0, placeHolder);

			Matcher m = nameExp.matcher(newNextStateName);

			//Error
			if(!m.matches())
			{
				System.out.println("FSM FILE ERROR: " + FSMname + " INVALID STATE NAME AT LINE " + line);
				//System.exit(0);
			}

			placeHolderTwo = connections.indexOf('{');

			if(placeHolder+1 != placeHolderTwo)
			{
				System.out.println("FSM FILE ERROR: " + FSMname + " NEXTSTATE WITHOUT TRANSITION VALUES AT LINE " + line);
				//System.exit(0);
			}


			connections = connections.substring(placeHolderTwo+1);

			//gets the end of the inputs
			placeHolderTwo = connections.indexOf("}");

			if(placeHolderTwo == -1)
			{
				System.out.println("FSM FILE ERROR: " + FSMname + " UNCLOSED SET AT LINE " + line);
				//System.exit(0);
			}


			placeHolder = connections.indexOf(':');

			//System.out.println(placeHolder +"   " + placeHolderTwo);

			//checks to see if the colon is in the right place
			if(placeHolder == -1)
				checker = connections.substring(placeHolderTwo+1);
			else 
			{
				if(placeHolder < placeHolderTwo)
				{
					System.out.println("FSM FILE ERROR: " + FSMname + " UNCLOSED SET AT LINE " + line);
					//System.exit(0);
				}

				checker = connections.substring(placeHolderTwo+1, placeHolder);
			}

			
			//gets the end of the inputs
			if( checker.lastIndexOf('}') != -1)
				placeHolderTwo = checker.lastIndexOf('}') + placeHolderTwo + 1;


			//cuts the inputs off to the ramaining inputs
			inputs = connections.substring(0, placeHolderTwo);	

			if(inputs.charAt(inputs.length()-1) == ',' && inputs.length() != 1)
			{
				System.out.println("FSM FILE ERROR: " + FSMname + " MISSING ELEMENT IN SET AT LINE " + line);
				//System.exit(0);
			}

			int x = 2;
			int trimer = inputs.length();
			
			//Makes the moore states
			if(!isMealy)
			{

				String input = inputs.charAt(0) + "";

				//Expands shortcuts
				if(input.equals("|"))
				{
					inputs = expand(inputs.charAt(1)) + inputs.substring(2); 
					input = inputs.charAt(0) + "";
				}
				if(nextStates.get(input) != null)
				{
					System.out.println("FSM FILE ERROR: " + FSMname + " MULTIPLE STATE TRANSITIONS FOR SINGLE INPUT ON LINE " + line);
					//System.exit(0);
				}

				//m = inAplha.matcher(input);
				//Makes sure characters are in the alphabet
				if(inputAlphabet.indexOf(input) == -1)
				{
					System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
					//System.exit(0);
				}

				//Adds the inputs to the hash map
				usedInputs += input;
				nextStateList.add (newNextStateName);
				
				nextStates.put(input, newNextStateName);

				//Adds the inputs to the hash map
				for(; x < inputs.length(); x+=2)
				{
					input = inputs.charAt(x) + "";
					//System.out.println(input);
					if(input.equals("|"))
					{
						if(inputs.length() <= x+1)
						{
							System.out.println("FSM FILE ERROR: " + FSMname + " INVALID SHORTCUT ON LINE " + line);
							//System.exit(0);
						}

						inputs = expand(inputs.charAt(x+1))  + inputs.substring(x+2); 
						//System.out.println(inputs);
						input = inputs.charAt(0) + "";
						x =0;

					}
					
					//Makes sure the comma is in the right spot
					else if(inputs.charAt(x-1) != ',')
					{
						System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITIONS AT " + line);
						//System.exit(0);
					}

					//System.out.println(input + "   " + newNextStateName);

					if(nextStates.containsKey(input))
					{

						//System.out.println("FDfddfs");
						if(!nextStates.get(input).equals(newNextStateName))
						{
							System.out.println("FSM FILE ERROR: " + FSMname + " NON-DETERMINISTIC TRANSITION TABLE FOR STATE " + newNextStateName);
							//System.exit(0);
						}
					}

					//m = inAplha.matcher(input);
					if(inputAlphabet.indexOf(input) == -1)
					{
						//System.out.println(input);
						//System.out.println ("HSHSHSH");
						System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
						//System.exit(0);
					}
					//Adds the inputs to the hash map
					usedInputs += input;
					nextStateList.add(newNextStateName);
					
					nextStates.put(input, newNextStateName);
				}
			}


			//The Mealy part
			//Neil is driving
			else
			{
				String input = inputs.charAt(0) + "";
				while( inputs != null)
				{

					input = inputs.charAt(0) + "";


					//Expands the shortcuts
					if(input.equals("|"))
					{
						placeHolder = inputs.indexOf("/");
//						if(placeHolder == -1 || placeHolder == inputs.length()-1)
//						{
//							System.out.println("NO STATED OUTPUT ON LINE " + line);
//							System.exit(0);
//						}
//						else 
						//Error
						if(placeHolder != 2)
						{
							System.out.println("FSM FILE ERROR: " + FSMname + "INVALID TRANSITION SYMBOL ON LINE " + line);
							//System.exit(0);
						}	

						output = inputs.substring(placeHolder+1);

						placeHolder = output.indexOf("/");
						
						//Error
						if(placeHolder != -1)
						{
							if(output.charAt(placeHolder-2) != ',')
							{
								System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
								//System.exit(0);
							}

							output = output.substring(0, placeHolder-2);
						}
						else
						{
							output = output.substring(0);
						}

						//System.out.println(output + " ssddf");
						
						//Expands the shortcuts
						inputs = expand(inputs.charAt(1), output) + inputs.substring(3); 
						input = inputs.charAt(0) + "";
					}



					placeHolder = inputs.indexOf("/");

//					if(placeHolder == -1 || placeHolder == inputs.length()-1)
//					{
//						System.out.println("NO STATED OUTPUT ON LINE " + line);
//						System.exit(0);
//					}


					//else 
					if(placeHolder != 1)
					{
						System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
						//System.exit(0);
					}	

					output = inputs.substring(placeHolder+1);

					placeHolder = output.indexOf("/");

					//Makes sure not to get array out of bounds error
					if(placeHolder != -1)
					{
						if(placeHolder < 2)
						{
							System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
							//System.exit(0);

						}

						if(output.charAt(placeHolder-2) != ',')
						{
							System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
							//System.exit(0);
						}

						inputs = inputs.substring(placeHolder+1);
						output = output.substring(0, placeHolder-2);
					}
					else
					{
						output = output.substring(0);
						inputs = null;
					}
					
					//Used to expand the shortcuts
					int temp= output.indexOf('|');

					if(temp != -1)
					{
						if(temp == output.length()-1)
						{
							System.out.println("FSM FILE ERROR: " + FSMname + " NO SHORTCUT VALUE ON LINE " + line);
							//System.exit(0);
						}

						if(temp+2 != output.length())
							output = output.substring(0, temp) + expandOutput(output.charAt(temp+1)) + output.substring(temp+2);
						else
							output = output.substring(0, temp) + expandOutput(output.charAt(temp+1));
					}

					//Makes the nextstate name concatated with the output
					output = newNextStateName + " " + output;

					if(inputAlphabet.indexOf(input) == -1)
					{
						System.out.println("FSM FILE ERROR: " + FSMname + " INVALID TRANSITION SYMBOL ON LINE " + line);
						//System.exit(0);
					}


					//Makes sure the input is not already used
					if(nextStates.containsKey(input))
					{

						if(!nextStates.get(input).equals(output))
						{
							if(newNextStateName.equals(nextStates.get(input).substring(0, nextStates.get(input).indexOf(" "))))
							{
								System.out.println("FSM FILE ERROR: " + FSMname + " MULTIPLE OUTPUTS LISTED FOR SINGLE TRANSITION FOR STATE " + newNextStateName);
							}
							else
							{
								System.out.println("FSM FILE ERROR: " + FSMname + " NON-DETERMINISTIC TRANSITION TABLE FOR STATE " + newNextStateName);
							}
								
							//System.exit(0);
						}
					}

					//Addes the input and output to the hashmap
					usedInputs += input;
					nextStateList.add(newNextStateName);
					
					nextStates.put(input, output);

				}

			}

			connections = connections.substring(trimer+1);

			//System.out.println(connections + " Con");

			placeHolder = connections.indexOf(':');
		}


		//Use Rejix to map the nextstates key is the input value
		//and the object is the next state

		return nextStates;

	}
	
	//Neil is driving
	//GEts the the next state 
	public String nextState(char input)
	{
		String finalOutput = "";
		if(isTrap)
			return name;	
		
		String temp = nextStates.get(input + "");
		//System.out.println("temp: " + temp);
		//System.out.println("input: " + input);
		
		validAlpha = allSymbols.matcher(input + "");
		//System.out.println("INPUT = " + input);
		//Also check to see is the Input Alphabet contains the value
		//to see if the value is there
		if(temp == null)
		{
			if(isUnspecified){
				//System.out.println("BLAH");
				return "UNLABELEDXXXXX";
			}
			
			
			System.out.println("FSM FILE ERROR: " + FSMname + " INVALID INPUT FOR STATE: " + name);
			return "";
		}
		//Makes sure the alphabet is valid
		else if(!validAlpha.matches())
		{
			//System.out.println("TEMP = " + temp);
			if(isMealy)
				System.out.println("FSM WARNING:" + FSMname + " UNSUPPORTED ALPHABET FOR " + name + "- OUTPUT IS Null");
			
			return "";
		}
		//Cehcks if it is a moore
		else if(!isMealy){
			//System.out.println("TEMP = " + temp);
			return temp;
		}
		else
		{
			int index = temp.indexOf(" ");
			//System.out.println("index " + index);
			String newState = temp.substring(0,index);
			String output = temp.substring(index+1);
			System.out.print(output);
			return newState;
		}
		
	}
	
	//Neil is driving
	//Expands the shortcuts
	public String expand(char input)
	{
		switch(input)
		{
			case 'u': return "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
			case 'l': return "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
			case 'a': return "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
			case 'd': return "0,1,2,3,4,5,6,7,8,9";
			case 'n': return "1,2,3,4,5,6,7,8,9";
			case 's': return ".,,,~,!,@,$,#,%,^,&,-,+,{,}";
			
			case 'U': return "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
			case 'L': return "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
			case 'A': return "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
			case 'D': return "0,1,2,3,4,5,6,7,8,9";
			case 'N': return "1,2,3,4,5,6,7,8,9";
			case 'S': return ".,,,~,!,@,$,#,%,^,&,-,+,{,}";
			
		}
		
		System.out.println("INVALID SHORTCUT ON LINE " + line);
		System.exit(0);
		
		return "";
	}
	
	//Neil is driving
	//Expands the shortcuts
	public String expand(char input, String o)
	{
		switch(input)
		{
			case 'u': return "A/"+o+",B/"+o+",C/"+o+",D/"+o+",E/"+o+",F/"+o+",G/"+o+",H/"+o+",I/"+o+",J/"+o+",K/"+o+",L/"+o+",M/"+o
							 +",N/"+o+",O/"+o+",P/"+o+",Q/"+o+",R/"+o+",S/"+o+",T/"+o+",U/"+o+",V/"+o+",W/"+o+",X/"+o+",Y/"+o+",Z/"+o;
			
			case 'l': return "a/"+o+",b/"+o+",c/"+o+",d/"+o+",e/"+o+",f/"+o+",g/"+o+",h/"+o+",i/"+o+",j/"+o+",k/"+o+",l/"+o+",m/"+o
							 +",n/"+o+",o/"+o+",p/"+o+",q/"+o+",r/"+o+",s/"+o+",t/"+o+",u/"+o+",v/"+o+",w/"+o+",x/"+o+",y/"+o+",z/"+o;
			
			case 'a': return "a/"+o+",b/"+o+",c/"+o+",d/"+o+",e/"+o+",f/"+o+",g/"+o+",h/"+o+",i/"+o+",j/"+o+",k/"+o+",l/"+o+",m/"+o
			 				 +",n/"+o+",o/"+o+",p/"+o+",q/"+o+",r/"+o+",s/"+o+",t/"+o+",u/"+o+",v/"+o+",w/"+o+",x/"+o+",y/"+o+",z/"+o
			 				 +"A/"+o+",B/"+o+",C/"+o+",D/"+o+",E/"+o+",F/"+o+",G/"+o+",H/"+o+",I/"+o+",J/"+o+",K/"+o+",L/"+o+",M/"+o
			 				 +",N/"+o+",O/"+o+",P/"+o+",Q/"+o+",R/"+o+",S/"+o+",T/"+o+",U/"+o+",V/"+o+",W/"+o+",X/"+o+",Y/"+o+",Z/"+o;
			
			case 'd': return "0/"+o+",1/"+o+",2/"+o+",3/"+o+",4/"+o+",5/"+o+",6/"+o+",7/"+o+",8/"+o+",9/"+o;
			
			case 'n': return "1/"+o+",2/"+o+",3/"+o+",4/"+o+",5/"+o+",6/"+o+",7/"+o+",8/"+o+",9/"+o;
			
			case 's': return "./"+o+",,/"+o+",~/"+o+",!/"+o+",@/"+o+",$/"+o+",#/"+o+",%/"+o+",^/"+o+",&/"+o+",-/"+o+",+/"+o+",{/"+o+",}/"+o;
			
			case 'U': return "A/"+o+",B/"+o+",C/"+o+",D/"+o+",E/"+o+",F/"+o+",G/"+o+",H/"+o+",I/"+o+",J/"+o+",K/"+o+",L/"+o+",M/"+o
							 +",N/"+o+",O/"+o+",P/"+o+",Q/"+o+",R/"+o+",S/"+o+",T/"+o+",U/"+o+",V/"+o+",W/"+o+",X/"+o+",Y/"+o+",Z/"+o;

			case 'L': return "a/"+o+",b/"+o+",c/"+o+",d/"+o+",e/"+o+",f/"+o+",g/"+o+",h/"+o+",i/"+o+",j/"+o+",k/"+o+",l/"+o+",m/"+o
			 				 +",n/"+o+",o/"+o+",p/"+o+",q/"+o+",r/"+o+",s/"+o+",t/"+o+",u/"+o+",v/"+o+",w/"+o+",x/"+o+",y/"+o+",z/"+o;

			case 'A': return "a/"+o+",b/"+o+",c/"+o+",d/"+o+",e/"+o+",f/"+o+",g/"+o+",h/"+o+",i/"+o+",j/"+o+",k/"+o+",l/"+o+",m/"+o
							 +",n/"+o+",o/"+o+",p/"+o+",q/"+o+",r/"+o+",s/"+o+",t/"+o+",u/"+o+",v/"+o+",w/"+o+",x/"+o+",y/"+o+",z/"+o
							 +"A/"+o+",B/"+o+",C/"+o+",D/"+o+",E/"+o+",F/"+o+",G/"+o+",H/"+o+",I/"+o+",J/"+o+",K/"+o+",L/"+o+",M/"+o
							 +",N/"+o+",O/"+o+",P/"+o+",Q/"+o+",R/"+o+",S/"+o+",T/"+o+",U/"+o+",V/"+o+",W/"+o+",X/"+o+",Y/"+o+",Z/"+o;

			case 'D': return "0/"+o+",1/"+o+",2/"+o+",3/"+o+",4/"+o+",5/"+o+",6/"+o+",7/"+o+",8/"+o+",9/"+o;

			case 'N': return "1/"+o+",2/"+o+",3/"+o+",4/"+o+",5/"+o+",6/"+o+",7/"+o+",8/"+o+",9/"+o;

			case 'S': return "./"+o+",,/"+o+",~/"+o+",!/"+o+",@/"+o+",$/"+o+",#/"+o+",%/"+o+",^/"+o+",&/"+o+",-/"+o+",+/"+o+",{/"+o+",}/"+o;
			
		}
		
		System.out.println("INVALID SHORTCUT ON LINE " + line);
		System.exit(0);
		
		return "";
	}
	
	//Neil is driving
	//Expands the shortcuts
	public String expandOutput(char input)
	{
		switch(input)
		{
			case 'u': return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			case 'l': return "abcdefghijklmnopqrstuvwxyz";
			case 'a': return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			case 'd': return "0123456789";
			case 'n': return "123456789";
			case 's': return ".,~!@$#%^&-+{}";
			
			case 'U': return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			case 'L': return "abcdefghijklmnopqrstuvwxyz";
			case 'A': return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			case 'D': return "0123456789";
			case 'N': return "123456789";
			case 'S': return ".,~!@$#%^&-+{}";
			
		}
		
		System.out.println("INVALID SHORTCUT ON LINE " + line);
		System.exit(0);
		
		return "";
	}
	
	public boolean isFinal(){
		return isFinal;
	}
	public String getOriginal()
	{
		return preConections;
	}
	
	//Adam is driving
	public String getName()
	{
		return name;
	}
	
	//Adam is Driving
	public boolean isMealy(){
		return isMealy;
	}
	//again
	public String getAlphabet(){
		return inputAlphabet;
	}
	//again
	public int getLine(){
		return line;
	}
	
	public boolean hasAccepting() 
	{
		return isFinal;
	}
	
	public Set<String> getAllNextSTates()
	{
		return nextStateList;
	}
	//adam
	public boolean isStart()
	{
		return isStart;
	}
	public boolean isTrap(){
		return isTrap;
	}
	
}
