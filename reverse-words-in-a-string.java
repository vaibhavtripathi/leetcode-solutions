class Solution {
    public String reverseWords(String s) {
        String newString = "";
        
        // trim() removes leading and trailing whitespaces
        // split by "\\s+" regex splits the string by any number of blank spaces
        String[] splitString = s.trim().split("\\s+");
        
        for (int i = splitString.length - 1; i > 0; i--) {
            newString += splitString[i] + " "; 
        }
        
        // Handle this separately because we don't want an extra blank in the end
        newString += splitString[0];
        return newString;
    }
}
