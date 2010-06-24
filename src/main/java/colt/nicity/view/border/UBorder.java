/*
 * UBorder.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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
package colt.nicity.view.border;

import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class UBorder {
	//----------------------------------------------------------------------------
    /**
     *
     * @param view
     */
    static public void activateBorder(IView view) {
		if (view == null) return;
		IBorder border = activateBorder(view.getBorder());
		if (border != null) {
			view.setBorder(border);
			view.paint();
		}
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param border
         * @return
         */
        static public IBorder activateBorder(IBorder border) {
		if (border == null) return null;
		if (border.isSelected() && !border.isActive()) {
			return border.getActiveSelectedBorder();
		}
		else if (!border.isActive()) {
			return border.getActiveBorder();
		}
		else return null;
	}
	
	//----------------------------------------------------------------------------
        /**
         *
         * @param view
         */
        static public void deactivateBorder(IView view) {
		if (view == null) return;
		IBorder border = deactivateBorder(view.getBorder());
		if (border != null) {
			view.setBorder(border);
			view.paint();
		}
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param border
         * @return
         */
        static public IBorder deactivateBorder(IBorder border) {
		if (border == null) return null;
		if (border.isSelected() && border.isActive()) return border.getSelectedBorder();
		else if (!border.isSelected() && border.isActive()) return border.getDefaultBorder();
		else return null;
	}
	
	
	//----------------------------------------------------------------------------
        /**
         *
         * @param view
         */
        static public void selectBorder(IView view) {
		selectBorder(view,true);
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param view
         * @param _flush
         */
        static public void selectBorder(IView view,boolean _flush) {
		if (view == null) return;
		IBorder border = selectBorder(view.getBorder());
		if (border == null) return;
		view.setBorder(border);
		view.repair();
		if (_flush) view.flush();
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param border
         * @return
         */
        static public IBorder selectBorder(IBorder border) {
		if (border == null) return null;
		if (border.isSelected()) return null;
		if (border.isActive()) return border.getActiveSelectedBorder();
		else return border.getSelectedBorder();
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param view
         */
        static public void deselectBorder(IView view) {
		deselectBorder(view,true);
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param view
         * @param _flush
         */
        static public void deselectBorder(IView view,boolean _flush) {
		if (view == null) return;
		IBorder border = deselectBorder(view.getBorder());
		if (border == null) return;
		view.setBorder(border);
		view.repair();
		if (_flush) view.flush();
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param border
         * @return
         */
        static public IBorder deselectBorder(IBorder border) {
		if (border == null) return null;
		if (!border.isSelected()) return null;
		if (border.isActive()) return border.getActiveBorder();
		else return border.getDefaultBorder();
	}
	//----------------------------------------------------------------------------
}
