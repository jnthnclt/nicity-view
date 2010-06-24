/*
 * ADK.java.java
 *
 * Created on 01-03-2010 01:30:23 PM
 *
 * Copyright 2010 Jonathan Colt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package colt.nicity.view.event;
/**
 *
 * @author Administrator
 */
public class ADK {
	// Event Families
	// famil,id
    /**
     *
     */
    final public static String[] family = new String[]{
		"null","Key","Mouse","Component"
	};
        /**
         *
         */
        final public static String[][] familyID = new String[][]{
		{},
		{"","Pressed","Released","Typed"},
		{"","Pressed","Dragged","Moved","Entered","Exited","Released","Clicked","Wheel"},
		{"","Focued","FocusLost","Opened","Closing","Activated","Deactivated","Iconified","Deiconified","Resized","LostFocus"}
	};
        /**
         *
         */
        final public static int cKey = 1;
        /**
         *
         */
        final public static int cKeyPressed = 1;
        /**
         *
         */
        final public static int cKeyReleased = 2;
                /**
                 *
                 */
                final public static int cKeyTyped = 3;
	
                /**
                 *
                 */
                final public static int cMouse = 2;
                /**
                 *
                 */
                final public static int cMousePressed = 1;
                /**
                 *
                 */
                final public static int cMouseDragged = 2;
                /**
                 *
                 */
                final public static int cMouseMoved = 3;
                /**
                 *
                 */
                final public static int cMouseEntered = 4;
                /**
                 *
                 */
                final public static int cMouseExited = 5;
                /**
                 *
                 */
                final public static int cMouseReleased = 6;
                /**
                 *
                 */
                final public static int cMouseClicked = 7;
                /**
                 *
                 */
                final public static int cMouseWheel = 8;
	
                /**
                 *
                 */
                final public static int cComponent = 3;
        /**
         *
         */
        final public static int cFocusGained = 1;
        /**
         *
         */
        final public static int cFocusLost = 2;
        /**
         *
         */
        final public static int cWindowOpened = 3;
                /**
                 *
                 */
                final public static int cWindowClosing = 4;
                /**
                 *
                 */
                final public static int cWindowActivated = 5;
                /**
                 *
                 */
                final public static int cWindowDeactivated = 6;
                /**
                 *
                 */
                final public static int cWindowIconified = 7;
                /**
                 *
                 */
                final public static int cWindowDeiconified = 8;
                /**
                 *
                 */
                final public static int cWindowResized = 9;
                /**
                 *
                 */
                final public static int cWindowLostFocus = 10;
	
}
