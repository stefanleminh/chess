import view.{GraphicUi, TextUi}
import controller.Controller
import model.{GameField, Player}
import state._
object chess {

  def loop(controller: Controller, textUi: TextUi): Unit ={

    textUi.draw()

    while (true){
     /* print("\n Select Figure\n")
      var x, y = scala.io.StdIn.readLine()
      print("\n Select Target position\n")
      var t_x, t_y = scala.io.StdIn.readLine()


      var ret = controller.putFigureTo((x.toInt,y.toInt), (t_x.toInt, t_y.toInt))
      if(ret.isInstanceOf[NoFigureException]){
        print("You don't have such a figure!\n")
      } else if(ret.isInstanceOf[NoAllowedMoveException])
        print("You're not supposed to make this move!\n")
      else if(ret.isInstanceOf[OwnTargetException])
        print("You cannot kill your own figures!\n")
      else{

        print("put " + (x,y).toString() + " to " + (t_x, t_y).toString())
        controller.setNextPlayer()
        print("Next Round. Player ist " + controller.currentPlayer)
      }*/
    }

  }


  def main(args: Array[String]): Unit ={
    // ui, controller objects
    var gamefield: GameField = new GameField()
    println("Please type Name Player A: ")
    val playerA = new Player(scala.io.StdIn.readLine())

    println("Please type Name Player B: ")
    val playerB = new Player(scala.io.StdIn.readLine())
    var controller = new Controller(new Player("sese"), new Player("sese"), gamefield)
    var textUi: TextUi = new TextUi("playerA", "playerB", gamefield, controller)
    val graphicUi = new GraphicUi(controller, gamefield)
    loop(controller, textUi)
  }
}