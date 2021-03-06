/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2017, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.timingdiagram;

import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;

public class CommandTimeMessage extends SingleLineCommand2<TimingDiagram> {

	public static final String PLAYER_CODE = "([\\p{L}_][\\p{L}0-9_.]*)";

	public CommandTimeMessage() {
		super(getRegexConcat());
	}

	private static RegexConcat getRegexConcat() {
		return new RegexConcat(new RegexLeaf("^"), //
				new RegexLeaf("PART1", PLAYER_CODE), //
				TimeTickBuilder.optionalExpressionAtWithArobase("TIME1"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("ARROW", "(-+)\\>"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("PART2", PLAYER_CODE), //
				TimeTickBuilder.optionalExpressionAtWithArobase("TIME2"), //
				new RegexLeaf("[%s]*"), //
				new RegexLeaf("MESSAGE", "(?::[%s]*(.*))?"), //
				new RegexLeaf("[%s]*$"));
	}

	@Override
	final protected CommandExecutionResult executeArg(TimingDiagram diagram, RegexResult arg) {
		final Player player1 = diagram.getPlayer(arg.get("PART1", 0));
		final Player player2 = diagram.getPlayer(arg.get("PART2", 0));
		final TimeTick tick1 = TimeTickBuilder.parseTimeTick("TIME1", arg, diagram);
		final TimeTick tick2 = TimeTickBuilder.parseTimeTick("TIME2", arg, diagram);
		diagram.createTimeMessage(player1, tick1, player2, tick2, arg.get("MESSAGE", 0));
		return CommandExecutionResult.ok();
	}

}
