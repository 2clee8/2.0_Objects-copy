//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
    public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image personPic;
    public Image starfishPic;
    public Image backgroundPic;
    public Image yachtPic;
    public Image seagullPic;


    //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Person person1;
//todo: make a new object called astro2
    public Person person2;
    public Starfish starfish1;
    public Starfish starfish2;
    public Yacht yacht;
    public Seagull seagull;

   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();

      // randomness
        //random range for x
        ///range 0-9
        int randx = (int)(Math.random() * 10);

        ///range 1-10
        randx = (int)(Math.random() * 10) + 1;

        ///range 1-1000
        randx = (int)(Math.random()*1000) + 1;

        //random range for y
        ///range 1-700
        int randy = (int)(Math.random()*700) + 1;


       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		personPic = Toolkit.getDefaultToolkit().getImage("person.png"); //load the picture
        starfishPic = Toolkit.getDefaultToolkit().getImage("starfish.png"); //load the picture
        yachtPic = Toolkit.getDefaultToolkit().getImage("yacht.png");
        backgroundPic = Toolkit.getDefaultToolkit().getImage("beach.png"); //load the picture
        seagullPic = Toolkit.getDefaultToolkit().getImage("seagull.png");

		person1 = new Person(200,300);
        person1.dx = -2;
        person1.dy = 3;

        person2 = new Person(randx,randy);
        person2.dx = 3;
        person2.dy = -2;
        person2.height = 100;
        person2.width = 100;

        starfish1 = new Starfish(randx,400);
//        starfish1.dx = 3;
//        starfish1.dy = -2;
        starfish1.height = 100;
        starfish1.width = 100;

        starfish2 = new Starfish(467,400);
//        starfish2.dx = 3;
//        starfish2.dy = -2;
        starfish2.height = 100;
        starfish2.width = 100;

        starfish1.dx = -starfish1.dx;

        yacht = new Yacht(300,310);
        yacht.height = 100;
        yacht.width = 100;

        seagull = new Seagull(300,320);
        seagull.height = 50;
        seagull.width = 50;

	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		person1.move();
        person2.move();
        starfish1.move();
        starfish2.move();
        yacht.move();
        seagull.move();
   Crashing();
	}

    public void Crashing() {
        //if starfish or Person crash into each other:
        if (person1.hitBox.intersects(person2.hitBox)) {
            System.out.println("Crash!");
            person1.dx = -person1.dx;
            person2.dx = -person2.dx;
            person1.dy = -person1.dy;
            person2.dy = -person2.dy;
        }

        if (starfish1.hitbox.intersects(starfish2.hitbox) && starfish2.isCrashing == false) {
            ;
            System.out.println("Asteroid collision");
            starfish2.height = starfish2.height + 10;
            starfish2.isCrashing = true;

        }
        // "!" flips it (so it's saying is it false if they're intersecting instead of is it true
        if (!seagull.hitbox.intersects(yacht.hitbox)) {
            yacht.isCrashing = false;
        }

        //if yacht and seagull crash into each other (make seagull larger):

        if (seagull.hitbox.intersects(yacht.hitbox) && yacht.isCrashing == false) {
            ;
            System.out.println("Seagull and yacht collision");
            seagull.height = seagull.height + 10 ;
            seagull.width = seagull.width + 10;
            yacht.isCrashing = true;


        }

        // ! flips it (so it's saying is it false if they're intersecting instead of is it true
        if (!seagull.hitbox.intersects(yacht.hitbox)) {
            yacht.isCrashing = false;
        }

    }
   //Pauses or sleeps the computer for the amount specified in milliseconds
     public void pause(int time){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
        }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
        //start
        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

      //draw the image of the astronaut
		g.drawImage(personPic, person1.xpos, person1.ypos, person1.width, person1.height, null);

        if(person2.isAlive == true){
            g.drawImage(personPic, person2.xpos, person2.ypos, person2.width, person2.height, null);
        }
        g.drawImage(starfishPic, starfish1.xpos, starfish1.ypos, starfish1.width, starfish1.height, null);
        g.drawImage(starfishPic, starfish2.xpos, starfish2.ypos, starfish2.width, starfish2.height, null);
        g.drawImage(yachtPic, yacht.xpos, yacht.ypos, yacht.width, yacht.height, null);
        g.drawImage(seagullPic, seagull.xpos, seagull.ypos, seagull.width, seagull.height, null);
        //g.drawRect(person1.hitBox.x,person1.hitBox.y,person1.hitBox.width,person1.hitBox.height);
        //g.drawRect(person2.hitBox.x,person2.hitBox.y,person2.hitBox.width,person2.hitBox.height);


        g.dispose();

		bufferStrategy.show();
	}
}