package edu.farmingdale.draganddropanim_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.farmingdale.draganddropanim_demo.ui.theme.DragAndDropAnim_DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ToDo 2: Show the DragAndDropBoxes composable
            DragAndDropAnim_DemoTheme {

                    DragAndDropBoxes()


            }
        }
    }
}



// This should be completed in a group setting
// ToDo x: Analyze the requirements for Individual Project 3 ---[Already-Completed]
// ToDo x: Show the DragAndDropBoxes composable --- [Already-Completed]
// ToDo x: Change the circle to a rect ---[Already-Completed]
// ToDo x: Replace the command right with a image or icon--- [Added]
// ToDo x: Make this works in landscape mode only--- [Already-Completed]
// ToDo x: Rotate the rect around itself --- [Already-Completed]
// ToDo x: Move - translate the rect horizontally and vertically  --- [Already-Completed]
// ToDo 8: Add a button to reset the rect to the center of the screen  -- {Completed}
// ToDo 9: Enable certain animation based on the drop event (like up or down)
// ToDo 10: Make sure to commit for each one of the above and submit this individually


