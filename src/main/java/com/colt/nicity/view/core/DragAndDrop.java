/*
 * DragAndDrop.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.event.AInputEvent;
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.interfaces.IDrag;
import com.colt.nicity.view.interfaces.IDrop;
import com.colt.nicity.view.interfaces.IDropMode;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IView;

//----------------------------------------------------------------------------	
// To effectivly drag and drop the two components must share a common DragAndDrop!
// Since the idea of multiple concurrent drag and drops with a single user is unusual
// if not impossible, the most common thing to do is use the static final cDefault.
// However I left DragAndDrop's constructor public so you could create multiple instances,
// which when distributed correctly allow a convienent way to partition drag and drops.
//----------------------------------------------------------------------------		
/**
 *
 * @author Administrator
 */
public class DragAndDrop {
	//----------------------------------------------------------------------------	
    /**
     *
     */
    public static final DragAndDrop cDefault = new DragAndDrop();
	//----------------------------------------------------------------------------	
	private IDrag dragFrom = null;
	private IDrop dropOn = null;
	private boolean dragged = false;
	//----------------------------------------------------------------------------
        /**
         *
         */
        protected IView scrollView = NullView.cNull;
	//----------------------------------------------------------------------------
        /**
         *
         */
        public DragAndDrop() {}
    //----------------------------------------------------------------------------
        /**
         *
         * @param _view
         * @return
         */
        public boolean isDragFrom(IView _view) {
        if (!dragged) return false;
        return _view == dragFrom;
    }
    //----------------------------------------------------------------------------
        /**
         *
         * @param _view
         * @return
         */
        public boolean isDropOn(IView _view) {
        return _view == dropOn;
    }
	//----------------------------------------------------------------------------
        /**
         *
         * @param _scrollView
         */
        public void setScrollView(IView _scrollView) {
		scrollView = (_scrollView == null) ? NullView.cNull : _scrollView;
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param e
         */
        synchronized public void mousePressed(MousePressed e) {
		Object source = e.getSource();
		if (!(source instanceof IDrag)) return;
		dragFrom = (IDrag)source;
        if (source instanceof IView) ((IView)source).paint();
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param e
         */
        synchronized public void mouseReleased(MouseReleased e) {
    	if (e.getClickCount() == 1) {
			IDropMode mode = canDeliver(e,dropOn);
            if (mode != null) {
				mode.drop(dropOn,dragFrom.getParcel(),e);
			}
            Object _dropOn = dropOn;
            Object _dragFrom = dragFrom;
            dropOn = null;
            dragFrom = null;
            if (_dropOn instanceof IView) ((IView)_dropOn).paint();
            if (_dragFrom instanceof IView) ((IView)_dragFrom).paint();
            dragged = false;
            scrollView = NullView.cNull;
            AInput.isDragging = false;
		}
		
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param e
         */
        synchronized public void mouseEntered(MouseEntered e) {
        Object source = e.getSource();
        if (dragFrom != null && source instanceof IDrop && source != dragFrom && canDeliver(e,source) != null) {
            dropOn = (IDrop)source;
            if (source instanceof IView) ((IView)source).paint();
        }
    }
	//----------------------------------------------------------------------------
        /**
         *
         * @param e
         */
        synchronized public void mouseExited(MouseExited e) {
        Object _dropOn = dropOn;
		dropOn = null;
        if (_dropOn instanceof IView) ((IView)_dropOn).paint();
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param e
         */
        synchronized public void mouseDragged(MouseDragged e) {
		if (scrollView != NullView.cNull && scrollView instanceof IMouseMotionEvents) {
			((IMouseMotionEvents)scrollView).mouseDragged(e);
		}
		if (e.getSumDeltaX() < 5 && e.getSumDeltaY() < 5) return;
        dragged = true;
        AInput.isDragging = true;
	}
	//----------------------------------------------------------------------------
	private IDropMode canDeliver(AInputEvent e,Object _address) {
		if (!(_address instanceof IDrop) || dragFrom == null) return null;
        return ((IDrop)_address).accepts(dragFrom.getParcel(),e);
	}
}
