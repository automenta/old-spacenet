package automenta.spacenet.test;

import java.util.List;

import junit.framework.TestCase;
import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.act.PossibleAction;
import automenta.spacenet.plugin.java.act.MethodActions;
import automenta.spacenet.plugin.java.act.StaticMethodsActions;
import automenta.spacenet.var.string.act.ToUppercase;

public class TestAction extends TestCase {

//	public void testOneAction() throws Exception {
//		PossibleAction a = new PossibleAction(new ToUppercase(), "string");
//		a.run();
//
//		assertEquals(1.0, a.getStrength());
//		assertEquals("STRING", a.getOutputValue());
//	}
//
//
//	public void testGetApplicableActions() throws Exception {
//		ActionIndex ai = new ActionIndex();
//
//		ai.addAction(new ToUppercase());
//		ai.addActionGenerator(new MethodActions());
//		ai.addActionGenerator(new StaticMethodsActions(Math.class));
//		ai.addActionGenerator(new StaticMethodsActions(Double.class));
//
//		List<PossibleAction> pa = ai.getPossibleActions("x");
//		assertTrue(pa.size() > 0);
//		System.out.println(pa);
//
//		List<PossibleAction> pa2 = ai.getPossibleActions(new Double(3.0));
//		assertTrue(pa2.size() > 0);
//		System.out.println(pa2);
//
//	}
}
