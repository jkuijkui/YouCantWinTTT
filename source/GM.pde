enum State 
{
  gameShowBeginUI, gameHumanFirstOn, gameCompuFirstOn, gameOverComputerWin, gameOverDraw, gameRestart
};

class GameManager
{
  Resource resource;
  State gameState;
  UI ui;

  HumanFirstOnLogic humanFirstOnLogic;

  CompuFirstOnLogic compuFirstOnLogic;

  int turnCount;

  boolean compuWin;
  boolean draw;

  GameManager()
  {
  }

  void Init()
  {
    LoadResource();

    InitGameEnvironment();
    InitGameState();
  }

  void InitGameEnvironment()
  {
    frameRate(8);

    rectMode(CENTER);

    ui = new UI();

    turnCount = 0;
  }

  void InitGameState()
  {
    gameState = State.gameShowBeginUI;

    humanFirstOnLogic = new HumanFirstOnLogic(this);

    compuFirstOnLogic = new CompuFirstOnLogic(this);

    compuWin = false;

    draw = false;
  }

  void LoadResource()
  {
    resource = new Resource();
  }

  void GameAutoMachineUpdate()
  {
    switch(gameState)
    {
    case gameShowBeginUI:
      ui.ShowBeginUI();
      if (ui.isHumanFirst)
      {
        gameState = State.gameHumanFirstOn;
        break;
      }
      if (ui.isCompuFirst)
      {
        gameState = State.gameCompuFirstOn;
        break;
      }
      break;
    case gameHumanFirstOn:
      ui.ShowGameOnUI();
      GameOnHumanFirstOn();
      break;
    case gameCompuFirstOn:
      ui.ShowGameOnUI();
      GameOnCompuFirstOn();
      break;
    case gameOverComputerWin:
      ui.ShowGameOverComputerWIn();
      if (ui.isRestart)
      {
        gameState = State.gameRestart;
        break;
      }
      break;
    case gameOverDraw:
      ui.ShowGameOverDraw();
      if (ui.isRestart)
      {
        gameState = State.gameRestart;
        break;
      }
      break;
    case gameRestart:
      Init();
      break;
    }
  }

  void GameOnHumanFirstOn()
  {
    humanFirstOnLogic.Update();
    resource.Update();

    if (compuWin)
    {
      gameState = State.gameOverComputerWin;
    }

    if (draw)
    {
      gameState = State.gameOverDraw;
    }
  }


  void GameOnCompuFirstOn()
  {
    compuFirstOnLogic.Update();
    resource.Update();

    if (compuWin)
    {
      gameState = State.gameOverComputerWin;
    }

    if (draw)
    {
      gameState = State.gameOverDraw;
    }
  }

  void GameUpdate()
  {
    GameAutoMachineUpdate();
  }
}