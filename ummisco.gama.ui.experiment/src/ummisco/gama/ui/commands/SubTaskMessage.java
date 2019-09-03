/*******************************************************************************************************
 *
 * msi.gama.common.SubTaskMessage.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package ummisco.gama.ui.commands;

import msi.gama.common.interfaces.gui.IGui;
import msi.gama.runtime.IStatusMessage;
import msi.gama.util.GamaColor;

/**
 * Class SubTaskMessage.
 *
 * @author drogoul
 * @since 5 nov. 2014
 *
 */
public class SubTaskMessage implements IStatusMessage {

	Double completion;
	String name;
	Boolean beginOrEnd;

	public SubTaskMessage(final String name, final boolean begin) {
		this.name = name;
		completion = null;
		this.beginOrEnd = begin;
	}

	public SubTaskMessage(final Double completion) {
		this.completion = completion;
		this.beginOrEnd = null;
	}

	/**
	 * Method getText()
	 * 
	 * @see msi.gama.runtime.IStatusMessage#getText()
	 */
	@Override
	public String getText() {
		return name;
	}

	/**
	 * Method getCode()
	 * 
	 * @see msi.gama.runtime.IStatusMessage#getCode()
	 */
	@Override
	public int getCode() {
		return IGui.NEUTRAL;
	}

	public Double getCompletion() {
		return completion;
	}

	public Boolean getBeginOrEnd() {
		return beginOrEnd;
	}

	/**
	 * Method getColor()
	 * 
	 * @see msi.gama.runtime.IStatusMessage#getColor()
	 */
	@Override
	public GamaColor getColor() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see msi.gama.common.IStatusMessage#getIcon()
	 */
	@Override
	public String getIcon() {
		return null;
	}

}
