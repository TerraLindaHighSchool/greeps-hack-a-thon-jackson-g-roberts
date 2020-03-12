import greenfoot.*;

/**
 * A Greep is an alien creature that likes to collect tomatoes.
 * 
 * @author (your name here)
 * @version 0.2
 */
public class Greep extends Creature
{
    // Remember: you cannot extend the Greep's memory. So:
    // no additional fields (other than final fields) allowed in this class!
    
    private static final int CIRCLE_TIME_THRESHOLD = 60;
    private static final int HOME_TURN_MODIFIER = 5;
    
    /**
     * Default constructor for testing purposes.
     */
    public Greep()
    {
        this(null);
    }
    
    /**
     * Create a Greep with its home space ship.
     */
    public Greep(Ship ship)
    {
        super(ship);
        
        setMemory(0);
        setFlag(1, false);
        setFlag(2, false);
    }

    /**
     * Do what a greep's gotta do.
     */
    public void act()
    {
        super.act();   // do not delete! leave as first statement in act().
        if (carryingTomato()) {
            setFlag(2, false);
            if (atShip()) {
                dropTomato();
                turn(180);
            }
            else {
                int pastRotation = getRotation();
                int turnModifier = 0;
                
                if (atWater())
                {
                    turn(-90);
                }
                
                turnHome();
                
                if (pastRotation < getRotation())
                {
                    turnModifier = HOME_TURN_MODIFIER;
                } else if (pastRotation > getRotation())
                {
                    turnModifier = HOME_TURN_MODIFIER * -1;
                } else {
                    turnModifier = 0;
                }
                
                setRotation(pastRotation);
                
                move();
                
                turn(turnModifier);
            }
        } else if (getFlag(2))
        {
            spit("red");
            turn(15);
            move();
            checkFood();
            if (getMemory() > CIRCLE_TIME_THRESHOLD) setFlag(2, false);
            setMemory(getMemory() + 1);
        } else {
            TomatoPile tomatoes = (TomatoPile) getOneIntersectingObject(TomatoPile.class);
            
            if (tomatoes != null)
            {
                turn(-30);
                setMemory(0);
                setFlag(2, true);
            } else {
                move();
            }
            
            checkFood();
        }
        
        if (atWorldEdge())
            {
                int x = getX();
                int y = getY();
                int rotation = getRotation();
                int hitWall = 0;
                
                if (x < 5)
                {
                    hitWall = 3;
                } else if (x > 795)
                {
                    hitWall = 1;
                } else if (y < 5)
                {
                    hitWall = 0;
                } else {
                    hitWall = 2;
                }
                
                switch (hitWall)
                {
                    case 0:
                        if (rotation > 270 || rotation < 90)
                        {
                            setRotation(90 - (rotation - 270));
                        } else {
                            setRotation(180 - (270 - rotation));
                        }
                        break;
                    case 1:
                        if (rotation > 0 && rotation < 180)
                        {
                            setRotation(90 + (90 - rotation));
                        } else {
                            setRotation(270 - (rotation - 270));
                        }
                        break;
                    case 2:
                        if (rotation > 270 || rotation < 90)
                        {
                            setRotation(90 - (rotation - 270));
                        } else {
                            setRotation(180 - (270 - rotation));
                        }
                        break;
                    case 3:
                        if (rotation > 0 && rotation < 180)
                        {
                            setRotation(90 + (90 - rotation));
                        } else {
                            setRotation(270 - (rotation - 270));
                        }
                        break;
                }
            }
            
            if (atWater()) 
            {
                turn(45);
            }
    }
    
    /**
     * Is there any food here where we are? If so, try to load some!
     */
    public void checkFood()
    {
        // check whether there's a tomato pile here
        TomatoPile tomatoes = (TomatoPile) getOneIntersectingObject(TomatoPile.class);
        if (tomatoes != null) {
            loadTomato();
            // Note: this attempts to load a tomato onto *another* Greep. It won't
            // do anything if we are alone here.
        }
    }

    /**
     * This method specifies the name of the author (for display on the result board).
     */
    public static String getAuthorName()
    {
        return "Anonymous";  // write your name here!
    }

    /**
     * This method specifies the image we want displayed at any time. (No need 
     * to change this for the competition.)
     */
    public String getCurrentImage()
    {
        if (carryingTomato()) {
            return "greep-with-food.png";
        }
        else {
            return "greep.png";
        }
    }
}