class UI
{
  class Button
  {
    PVector location;
    float xSize;
    float ySize;
    float cornerSize;

    String string;

    color oldColor;
    color newColor;

    boolean isClicked;
    
    boolean soundLock;
    
    Button(PVector _location, float _xSize, String _string)
    {
      location = _location;
      xSize = _xSize;
      ySize = xSize/2.0f;
      cornerSize = xSize/4.0f;

      oldColor = #F36B6B;
      newColor = #ECE58A;

      string = _string;

      isClicked = false;
      
      soundLock  = false;
    }

    void Draw()
    {       
      rect(location.x, location.y, xSize, ySize, cornerSize);
      pushStyle();
      fill(#1FB57B);
      strokeWeight(6);
      textSize(14);
      text(string, location.x, location.y);
      popStyle();
    }

    boolean IsInRect()
    {
      return (abs(mouseX - location.x) <= xSize/2.0f) && (abs(mouseY - location.y) <= ySize/2.0f);
    }

    void CheckIfMouseOverAndChangeColor()
    {
      if (IsInRect())
      {
        fill(newColor);
      } 
      else
      {
        fill(oldColor);
      }
    }

    void Update()
    {
      if(IsInRect() && !soundLock)
      {
        
        clip.play();
        
        soundLock = true;
      }
      
      if(!IsInRect())
      {
        clip.rewind();
        soundLock = false;
      }
      
      pushStyle();
      CheckIfMouseOverAndChangeColor(); 
      Draw();
      popStyle();
    }

    void MouseListener()
    {
      if (IsInRect())
      {
        isClicked = true;
      }
    }
  }

  PImage background;
  PFont font;

  Button humanFirst;
  Button compuFirst;
  
  Button restart;
  
  float tempLoacation;

  boolean ShowBeginUILock;
  boolean ShowGameOnUILock;

  boolean isHumanFirst;
  boolean isCompuFirst;
  
  boolean isRestart;
  
  UI()
  {
    background = loadImage("data/background.PNG");
    font = loadFont("Lolita-48.vlw");

    tempLoacation = width/2.0f;

    humanFirst = new Button(new PVector(tempLoacation, tempLoacation), 90, "Human First");
    compuFirst = new Button(new PVector(tempLoacation, tempLoacation * 1.5f), 90, "Compu First");
    restart = new Button(new PVector(tempLoacation, tempLoacation * 1.25f), 90, "Restart");
    
    isHumanFirst = false;
    isCompuFirst = false;
    
    isRestart = false;
    
    ShowBeginUILock = false;
    ShowGameOnUILock = false;

    imageMode(CENTER);
    textFont(font, 50);
    textAlign(CENTER, CENTER);
  }

  void ShowBeginUI()
  {
    if (!ShowBeginUILock)
    {
      image(background, tempLoacation, tempLoacation, width, height);
      pushStyle();
      fill(#FAF321);
      strokeWeight(6);
      text("Tic-tac-toe", width/2.0f, height/4.0f);
      popStyle();
      ShowBeginUILock = true;
    }

    humanFirst.Update();
    compuFirst.Update();

    if (humanFirst.isClicked)
    {
      isHumanFirst = true;
      return;
    }

    if (compuFirst.isClicked)
    {
      isCompuFirst = true;
      return;
    }
  }

  void ShowGameOnUI()
  {
    image(background, tempLoacation, tempLoacation, width, height);
    ShowGameOnUILock = true;
  }

  void ShowGameOverComputerWIn()
  {
    pushStyle();
    fill(#0831FA);
    textSize(40);
    strokeWeight(20);
    text("COMPUTER WIN", width/2.0f, height/4.0f,width,height);
    popStyle();
    
    restart.Update();
    
    if (restart.isClicked)
    {
      isRestart = true;
    }
  }
  
  void ShowGameOverDraw()
  {
    pushStyle();
    fill(#F07D11);
    textSize(40);
    strokeWeight(20);
    text("Game Draw", width/2.0f, height/4.0f,width,height);
    popStyle();
    
    restart.Update();
    
    if (restart.isClicked)
    {
      isRestart = true;
    }
  }
}