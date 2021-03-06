/**
 * 
 * For information on usage and redistribution, and for a DISCLAIMER OF ALL
 * WARRANTIES, see the file, "LICENSE.txt," in this distribution.
 * 
 */

package org.puredata.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * 
 * Some tests for messaging and MIDI support in libpd.  Basically, this class tests everything except
 * the parts of libpd that matter.  If you have any idea how to write tests for the signal processing
 * part, please let me know.
 * 
 * @author Peter Brinkmann (peter.brinkmann@gmail.com) 
 *
 */
public class PdBaseTest {

	private static int patch;
	
	private PdReceiver receiver;
	private PdMidiReceiver midiReceiver;

	@BeforeClass
	public static void loadPatch() throws IOException {
		PdBase.subscribe("eggs");
		patch = PdBase.openPatch("javatests/org/puredata/core/test_java.pd");
	}
	
	@AfterClass
	public static void closePatch() {
		PdBase.release();
	}
	
	@Before
	public void setUp() {
		receiver = EasyMock.createStrictMock(PdReceiver.class);
		midiReceiver = EasyMock.createStrictMock(PdMidiReceiver.class);
		PdBase.setReceiver(receiver);
		PdBase.setMidiReceiver(midiReceiver);
	}
	
	@After
	public void tearDown() {
		// Do nothing; just here for symmetry.
	}

	@Test
	public void testBlockSize() {
		assertEquals(64, PdBase.blockSize());
	}
	
	@Test
	public void testDollarZero() {
		assertEquals(1002, patch);
	}
	
	@Test
	public void testSubscription() {
		assertFalse(PdBase.exists("baz"));
		assertEquals(-1, PdBase.subscribe(null));
		assertEquals(0, PdBase.subscribe("baz"));
		assertTrue(PdBase.exists("baz"));
		PdBase.unsubscribe(null);
		PdBase.unsubscribe("");
		PdBase.unsubscribe("baz");
		assertFalse(PdBase.exists("baz"));
	}
	
	@Test
	public void testPrint() {
		receiver.print("print: 0\n");
		receiver.print("print: 42\n");
		receiver.print("print: symbol");
		receiver.print(" ");
		receiver.print("don't panic");
		receiver.print("\n");
		EasyMock.replay(receiver);
		PdBase.sendFloat("foo", 0);
		PdBase.sendFloat("foo", 42);
		PdBase.sendSymbol("foo", "don't panic");
		EasyMock.verify(receiver);
	}

	@Test
	public void testReceive() {
		receiver.receiveBang("eggs");
		receiver.receiveFloat("eggs", 42);
		receiver.receiveSymbol("eggs", "hund katze maus");
		receiver.receiveList("eggs", "hund", 1.0f, "katze", 2.0f, "maus", 3.0f);
		receiver.receiveMessage("eggs", "testing", "one", 1.0f, "two", 2.0f);
		EasyMock.replay(receiver);
		PdBase.sendBang("spam");
		PdBase.sendFloat("spam", 42);
		PdBase.sendSymbol("spam", "hund katze maus");
		PdBase.sendList("spam", "hund", 1, "katze", 2, "maus", 3);
		PdBase.sendMessage("spam", "testing", "one", 1, "two", 2);
		EasyMock.verify(receiver);
	}
	
	@Test
	public void testNullReceiver() {
		EasyMock.replay(receiver);
		PdBase.setReceiver(null);
		PdBase.sendBang("spam");
		PdBase.sendFloat("spam", 42);
		PdBase.sendSymbol("spam", "hund katze maus");
		PdBase.sendList("spam", "hund", 1, "katze", 2, "maus", 3);
		PdBase.sendMessage("spam", "testing", "one", 1, "two", 2);
		EasyMock.verify(receiver);
	}

	@Test
	public void testNoteOn() {
		midiReceiver.receiveNoteOn(0, 64, 127);
		midiReceiver.receiveNoteOn(12, 0, 127);
		midiReceiver.receiveNoteOn(30, 10, 15);
		EasyMock.replay(midiReceiver);
		assertEquals(0, PdBase.sendNoteOn(0, 64, 127));
		assertEquals(0, PdBase.sendNoteOn(12, 0, 127));
		assertEquals(0, PdBase.sendNoteOn(30, 10, 15));
		assertEquals(-1, PdBase.sendNoteOn(-1, 64, 127));
		assertEquals(-1, PdBase.sendNoteOn(12, -1, 127));
		assertEquals(-1, PdBase.sendNoteOn(30, 10, -1));
		assertEquals(-1, PdBase.sendNoteOn(12, 128, 127));
		assertEquals(-1, PdBase.sendNoteOn(30, 10, 128));
		EasyMock.verify(midiReceiver);
	}

	@Test
	public void testControlChange() {
		midiReceiver.receiveControlChange(0, 64, 127);
		midiReceiver.receiveControlChange(12, 0, 127);
		midiReceiver.receiveControlChange(30, 10, 15);
		EasyMock.replay(midiReceiver);
		assertEquals(0, PdBase.sendControlChange(0, 64, 127));
		assertEquals(0, PdBase.sendControlChange(12, 0, 127));
		assertEquals(0, PdBase.sendControlChange(30, 10, 15));
		assertEquals(-1, PdBase.sendControlChange(-1, 64, 127));
		assertEquals(-1, PdBase.sendControlChange(12, -1, 127));
		assertEquals(-1, PdBase.sendControlChange(30, 10, -1));
		assertEquals(-1, PdBase.sendControlChange(12, 128, 127));
		assertEquals(-1, PdBase.sendControlChange(30, 10, 128));
		EasyMock.verify(midiReceiver);
	}

	@Test
	public void testPolyAftertouch() {
		midiReceiver.receivePolyAftertouch(0, 64, 127);
		midiReceiver.receivePolyAftertouch(12, 0, 127);
		midiReceiver.receivePolyAftertouch(30, 10, 15);
		EasyMock.replay(midiReceiver);
		assertEquals(0, PdBase.sendPolyAftertouch(0, 64, 127));
		assertEquals(0, PdBase.sendPolyAftertouch(12, 0, 127));
		assertEquals(0, PdBase.sendPolyAftertouch(30, 10, 15));
		assertEquals(-1, PdBase.sendPolyAftertouch(-1, 64, 127));
		assertEquals(-1, PdBase.sendPolyAftertouch(12, -1, 127));
		assertEquals(-1, PdBase.sendPolyAftertouch(30, 10, -1));
		assertEquals(-1, PdBase.sendPolyAftertouch(12, 128, 127));
		assertEquals(-1, PdBase.sendPolyAftertouch(30, 10, 128));
		EasyMock.verify(midiReceiver);
	}

	@Test
	public void testProgramChange() {
		midiReceiver.receiveProgramChange(0, 64);
		midiReceiver.receiveProgramChange(12, 0);
		midiReceiver.receiveProgramChange(30, 127);
		EasyMock.replay(midiReceiver);
		assertEquals(0, PdBase.sendProgramChange(0, 64));
		assertEquals(0, PdBase.sendProgramChange(12, 0));
		assertEquals(0, PdBase.sendProgramChange(30, 127));
		assertEquals(-1, PdBase.sendProgramChange(-1, 64));
		assertEquals(-1, PdBase.sendProgramChange(12, -1));
		assertEquals(-1, PdBase.sendProgramChange(10, 128));
		EasyMock.verify(midiReceiver);
	}

	@Test
	public void testAftertouch() {
		midiReceiver.receiveAftertouch(0, 64);
		midiReceiver.receiveAftertouch(12, 0);
		midiReceiver.receiveAftertouch(30, 127);
		EasyMock.replay(midiReceiver);
		assertEquals(0, PdBase.sendAftertouch(0, 64));
		assertEquals(0, PdBase.sendAftertouch(12, 0));
		assertEquals(0, PdBase.sendAftertouch(30, 127));
		assertEquals(-1, PdBase.sendAftertouch(-1, 64));
		assertEquals(-1, PdBase.sendAftertouch(12, -1));
		assertEquals(-1, PdBase.sendAftertouch(10, 128));
		EasyMock.verify(midiReceiver);
	}

	@Test
	public void testPitchBend() {
		midiReceiver.receivePitchBend(0, 64);
		midiReceiver.receivePitchBend(12, 0);
		midiReceiver.receivePitchBend(30, 8191);
		midiReceiver.receivePitchBend(8, -237);
		midiReceiver.receivePitchBend(10, -8192);
		EasyMock.replay(midiReceiver);
		assertEquals(0, PdBase.sendPitchBend(0, 64));
		assertEquals(0, PdBase.sendPitchBend(12, 0));
		assertEquals(0, PdBase.sendPitchBend(30, 8191));
		assertEquals(0, PdBase.sendPitchBend(8, -237));
		assertEquals(0, PdBase.sendPitchBend(10, -8192));
		assertEquals(-1, PdBase.sendPitchBend(-1, 64));
		assertEquals(-1, PdBase.sendPitchBend(12, -8193));
		assertEquals(-1, PdBase.sendPitchBend(12, 8192));
		EasyMock.verify(midiReceiver);
	}
	
	@Test
	public void testNullMidiReceiver() {
		EasyMock.replay(midiReceiver);
		PdBase.setMidiReceiver(null);
		assertEquals(0, PdBase.sendNoteOn(30, 10, 15));
		assertEquals(0, PdBase.sendControlChange(12, 0, 127));
		assertEquals(0, PdBase.sendPolyAftertouch(0, 64, 127));
		assertEquals(0, PdBase.sendAftertouch(12, 0));
		assertEquals(0, PdBase.sendPitchBend(0, 64));
		assertEquals(0, PdBase.sendProgramChange(30, 127));
		EasyMock.verify(midiReceiver);
	}
	
	@Test
	public void testArrayAccess() {
		int n = 128;
		assertEquals(n, PdBase.arraySize("array1"));
		float[] u = new float[n];
		float[] v = new float[n];
		for (int i = 0; i < n; i++) {
			u[i] = i;
		}
		PdBase.writeArray("array1", 0, u, 0, n);
		PdBase.readArray(v, 0, "array1", 0, n);
		for (int i = 0; i < n; i++) {
			assertEquals(u[i], v[i], 0);
		}
	}
}
