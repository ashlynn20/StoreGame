public class NonPlayableCharacter {
    public boolean playable = false;
    public boolean resting = true;
    public String name;
    public String action = "default";

    public static void main(String[] args) {
        NonPlayableCharacter nonPlayableCharacterOne = new NonPlayableCharacter("Fred");
        if(!nonPlayableCharacterOne.playable){
            if(!nonPlayableCharacterOne.resting){
                switch (nonPlayableCharacterOne.action){
                    case "talking":
                        nonPlayableCharacterOne.resting = false;
                        System.out.println(nonPlayableCharacterOne.name + ": What a marvelous day!");
                        nonPlayableCharacterOne.resting = true;
                        nonPlayableCharacterOne.action = "default";
                        break;
                    case "moving":
                        //insert functionality for moving
                        //this will take several lines
                        //of code
                    case "eating":
                        //insert functionality eating
                        //this will take several lines
                        //of code
                    case "drinking":
                        //insert functionality for drinking
                        //this will take several lines
                        //of code
                        break;
                    default:
                        nonPlayableCharacterOne.defaultFunctionality();
                }
            }
        }
        //resting state
    } 

    public NonPlayableCharacter(String name){
        this.name = name;
    }

    public void defaultFunctionality(){
        //do nothing
        //or do something 
    }
}