// Special Comments
//  - TODO: This should be checked over later, when the code is complete enough that functions
//      call this or interact with it
//  - NOTE: There's likely something that differs between the Java and C versions here

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Output2 {
	// m_pd.h
	class t_symbol {
		char[] s_name;
		Object s_thing;
		t_symbol s_next;
	}

	class gp_un2 { }
	class gp_un2_glist { Object gs_glist; } //TODO: could be more specific than 'Object'
	class gp_un2_array { Object[] gs_array; } //TODO: could be more specific than 'Object'
	class t_gstub {
		gp_un2 gp_un;
		int gs_which;
		int gs_refcount;
	}

	class gp_un1 { }
	class gp_un1_scalar { Object gp_scalar; }
	class gp_un1_word   { Object gp_w; }
	class t_gpointer {
		gp_un1 gp_un;
		int gp_valid;
		t_gstub gp_stub;
	}

	class t_word { }
	class t_word_float    extends t_word { float      w_float; }
	class t_word_symbol   extends t_word { t_symbol   w_symbol; }
	class t_word_gpointer extends t_word { t_gpointer w_gpointer; }
	class t_word_array    extends t_word { Object[]   w_array; }
	class t_word_list     extends t_word { Object     w_list; }
	class t_word_index    extends t_word { int        w_index; }

	enum t_atomtype {
		A_NULL, A_FLOAT, A_SYMBOL, A_POINTER, A_SEMI, A_COMMA,
		A_DEFFLOAT, A_DEFSYM, A_DOLLAR, A_DOLLSYM, A_GIMME, A_CANT
	}

	class t_atom {
		t_atomtype a_type;
		t_word a_w;
	}

	// m_binbuf.c
	class t_binbuf {
		int b_n;
		t_atom b_vec;
	}

	// s_inter.c
	/* Skip: (Unix-only functions)
	    -sys_signal:217
	    -sys_exithandler:230
	    -sys_alarmhandler:242
	    -sys_huphandler:247
	    -sys_setalarm:255
			-sys_setpriority:322 */
	/* Skip: (Networking functions, used for communication with GUI)
			-sys_sockerror:347 */
	//Skip: sys_exit:491 - defined in m_sched.c
	/* Skip: (sockets)
			-t_socketreceiver:85 (struct)
			-socketreceiver_new:402
			-socketreceiver_new:402
			-socketreceiver_free:415
			-socketreceiver_doread:423
			-socketreceiver_getudp:454
			-socketreceiver_read:493
			-sys_closesocket:560 */
	/* Skip: (gui-related code)
		Struct:
			-t_guiqueue:575
		Members:
			-sys_guiqueuehead:583
			-sys_guibuf:584
			-sys_guibufhead:585
			-sys_guibuftail:586
			-sys_guibufsize:587
			-sys_waitingforping:588
			-sys_bytessincelastping:589
		Funcs:
			-sys_trytogetmoreguibuf:591
			-sys_vgui:641
			-sys_gui:690
			-sys_flushtogui:695
			-glob_ping:729
			-sys_flushqueue:734
			-sys_poll_togui:768
			-sys_pretendguibytes:789
			-sys_queuegui:794
			-sys_unqueuegui:817
			-sys_pollgui:836
			-glob_watchdog:848
			-sys_startgui:866
	*/

	class t_fdpoll {
		//NOTE: uses File instead of a file descriptor (int)
		File fdp_fd;
		Function2<Void, Integer, File> fdp_fn;
		int fdp_ptr; //TODO: pointer - what's this for?
	}

	public static int      INBUFSIZE = 4096;
	public static int      sys_guisetportnumber;
	public static List     sys_fdpoll = new ArrayList<t_fdpoll>();
	public static int      sys_maxfd = 0;
	public static int      sys_guisock;
	public static t_binbuf inbinbuf;
	public static long     then = 0;
	public static int      reentered = 0; //inline static from s_inter.c:1277

	//NOTE: original c function returns double
	public static long sys_getrealtime() {
		long now = System.nanoTime();
		if( then == 0 )
			then = now;
		return now - then;
	}

	//NOTE: changed int to bool (return type + pollem)
	public static boolean sys_domicrosleep(int microsec, boolean pollem) {
		if( pollem ) {
			boolean didsomething = false;
			Iterator it = sys_fdpoll.iterator();
			while( it.hasNext() ) {
				if( !sys_nosleep )
					TimeUnit.MICROSECONDS.sleep((long)microsec);

				t_fdpoll fd = it.next();
				if( fd.fdp_fd.canRead() ) {
					fd.fdp_fn.eval(fd.fdp_ptr, fd.fdp_fd);
					didsomething = true;
				}
			}
			return didsomething;
		} else {
			TimeUnit.MICROSECONDS.sleep((long)microsec);
			return false;
		}
	}

	public static void sys_microsleep(int microsec) {
		sys_domicrosleep(microsec, true);
	}

	public static void sys_addpollfn(int fd, Function2 fn, int ptr) {
		t_fdpoll pollfn = new t_fdpoll();
		pollfn.fdp_fd  = fd;
		pollfn.fdp_fn  = fn;
		pollfn.fdp_ptr = ptr;
		sys_fdpoll.add(pollfn);

		if( fd >= sys_maxfd )
			sys_maxfd = fd + 1;
	}

	public static void sys_rmpollfn(int fd) {
		Iterator it = sys_fdpoll.iterator();
		while( it.hasNext() ) {
			if( it.next().fdp_fd == fd ) {
				it.remove();
				return;
			}
		}
	}

	public static void sys_bail(int n) {
		if( !reentered ) {
			reentered = 1;
			System.err.println("closing audio...");
			sys_close_audio();
			System.err.println("closing midi...");
			sys_close_midi();
			exit(n);
		} else
			_exit(1);
	}

	public static void glob_quit() {
		// there was some gui stuff going on here in the c-file
		sys_bail(0);
	}
}
