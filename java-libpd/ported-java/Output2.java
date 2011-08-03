//AT:179
// Special Comments
//  - TODO: This should be checked over later, when the code is complete enough that functions
//      call this or interact with it
//  - NOTE: There's likely something that differs between the Java and C versions here

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Output2 {
	// m_imp.h

	// m_pd.h
	final static int PD_MAJOR_VERSION = 0;
	final static int PD_MINOR_VERSION = 43;
	final static int PD_BUGFIX_VERSION = 0;
	final static String PD_TEST_VERSION = "";

	final static int MAXPDSTRING = 1000;
	final static int MAXPDARG = 5;

	final static int GP_NONE  = 0;
	final static int GP_GLIST = 1;
	final static int GP_ARRAY = 2;

	final static int T_TEXT = 0;
	final static int T_OBJECT = 1;
	final static int T_MESSAGE = 2;
	final static int T_ATOM = 3;

	final static int CLASS_DEFAULT = 0;
	final static int CLASS_PD = 1;
	final static int CLASS_GOBJ = 2;
	final static int CLASS_PATCHABLE = 3;
	final static int CLASS_NOINLET = 8;
	final static int CLASS_TYPEMASK = 3;

	final static int MAXLOGSIG = 32;
	final static int MAXLOGSIZE = (1 << MAXLOGSIG);

	final static int LOGCOSTABSIZE = 9;
	final static int COSTABSIZE = (1 << LOGCOSTABSIZE);

	final static boolean PD_USE_TE_XPIX = true; //m_pd.h:640

	class t_symbol {
		String s_name;
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

	//NOTE: possibly unneeded, see m_pd.h:173, m_pd.h:179
	class t_gobj {
		t_pd g_pd; //TODO: add t_pd: *t_class: _class - m_imp.h:31
		t_gobj g_next;
	}

	class t_scalar {
		t_gobj sc_gobj;
		t_symbol sc_template;
		t_word[] sc_vec;
	}

	class t_text {
		t_gobj te_g;
		t_binbuf te_binbuf;
		t_outlet te_outlet;
		t_inlet te_inlet;
		short te_xpix;
		short te_ypix;
		short te_width;
		int te_type;
	}

	public static void SETSEMI(t_atom atom) {
		atom.a_type = A_SEMI;
		atom.a_w = new t_word_index();
		atom.a_w.w_index = 0;
	}

	public static void SETCOMMA(t_atom atom) {
		atom.a_type = A_COMMA;
		atom.a_w = new t_word_index();
		atom.a_w.w_index = 0;
	}

	public static void SETPOINTER(t_atom atom, t_gpointer gp) {
		atom.a_type = A_POINTER;
		atom.a_w = new t_word_gpointer();
		atom.a_w.w_gpointer = gp;
	}

	public static void SETFLOAT(t_atom atom, float f) {
		atom.a_type = A_FLOAT;
		atom.a_w = new t_word_float();
		atom.a_w.w_float = f;
	}

	public static void SETSYMBOL(t_atom atom, t_symbol s) {
		atom.a_type = A_SYMBOL;
		atom.a_w = new t_word_symbol();
		atom.a_w.w_symbol = s;
	}

	public static void SETDOLLAR(t_atom atom, int n) {
		atom.a_type = A_DOLLAR;
		atom.a_w = new t_word_index();
		atom.a_w.w_index = n;
	}

	public static void SETDOLLSYM(t_atom atom, t_symbol s) {
		atom.a_type = A_DOLLSYM;
		atom.a_w = new t_word_symbol();
		atom.a_w.w_symbol = s;
	}

	class t_signal {
		int s_n;
		t_sample s_vec; //TODO: where's t_sample from?
		t_float s_sr;
		int s_refcount;
		int s_isborrowed;
		t_signal s_borrowedfrom;
		t_signal s_nextfree;
		t_signal s_nextused;
		int s_vecsize;
	}

	class t_resample {
		int method;
		long downsample;
		long upsample;
		t_sample s_vec;
		int s_n;
		t_sample coeffs;
		int coefsize;
		t_sample buffer;
		int bufsize;
	}

	// m_class.c

	// m_pd.c:226
	public static t_class bindlist_class;

	public static t_pd pd_new(t_class c) {
		//NOTE: What? check if t_class, t_pd, t_text, t_object are actually all the same
		if(c.patchable) {
			c.ob_inlet = 0;
			c.ob_outlet = 0;
		}
		return c;
	}

	public static void pd_free(t_pd x) {
		if(x.c_freemethod)
			x.c_freemethod.eval(x);
		/*if(x.c_patchable) {
			//NOTE: this is probably unneccessary, Java GC should take care of it.
			// these methods are not implemented
			outlet_free(x.ob_outlet);
			inlet_free(x.ob_inlet);
			binbuf_free(x.ob_binbuf);
		}*/
	}

	public static void gobj_save(t_gobj x, t_binbuf b) {
		if(x.g_pd.c_savefn)
			x.g_pd.c_savefn.eval(x, b);
	}

	class t_bindelem {
		t_pd e_who;
		t_bindelem e_next;
	}

	class t_bindlist {
		t_pd b_pd;
		t_bindelem b_list;
	}

	public class bindlist_bang implements Function1<void,t_bindlist> {
		public void eval(t_bindlist x) {
			for(t_bindelem e = x.b_list; e; e = e.next)
				pd_bang(e.e_who);
		}
	}

	public class bindlist_float implements Function2<void,t_bindlist,t_float> {
		public void eval(t_bindlist x, t_float f) {
			for(t_bindelem e = x.b_list; e; e = e.next)
				pd_float(e.e_who, f);
		}
	}

	public class bindlist_symbol implements Function2<void,t_bindlist,t_symbol> {
		public void eval(t_bindlist x, t_symbol s) {
			for(t_bindelem e = x.b_list; e; e = e.next)
				pd_symbol(e.e_who, f);
		}
	}

	public class bindlist_pointer implements Function2<void,t_bindlist,t_gpointer> {
		public void eval(t_bindlist x, t_gpointer gp) {
			for(t_bindelem e = x.b_list; e; e = e.next)
				pd_pointer(e.e_who, gp);
		}
	}

	public class bindlist_list implements Function4<void,t_bindlist,t_symbol,int,t_atom[]> {
		//NOTE: could be pointer or array, should be checked
		public void eval(t_bindlist x, t_symbol s, int argc, t_atom[] argv) {
			for(t_bindelem e = x.b_list; e; e = e.next)
				pd_list(e.e_who, s, argc, argv);
		}
	}

	public class bindlist_anything implements Function4<void,t_bindlist,t_symbol,int,t_atom[]> {
		public void eval(t_bindlist x, t_symbol s, int argc, t_atom[] argv) {
			for(t_bindelem e = x.b_list; e; e = e.next)
				pd_typedmess(e.e_who, s, argc, argv);
		}
	}

	public static void m_pd_setup() {
		bindlist_class = class_new(gensym("bindlist"), 0, 0, t_bindlist, CLASS_PD, 0);
		class_addbang(bindlist_class, new bindlist_bang());
		class_addfloat(bindlist_class, new bindlist_float());
		class_addsymbol(bindlist_class, new bindlist_symbol());
		class_addpointer(bindlist_class, new bindlist_pointer());
		class_addlist(bindlist_class, new bindlist_list());
		class_addanything(bindlist_class, new bindlist_anything());
	}

	public static void pd_bind(t_pd x, t_symbol s) {
		if( s.s_thing ) {
			if( s.s_thing == bindlist_class ) {
				t_bindelem e = new t_bindelem();
				e.e_next = s.s_thing.b_list;
				e.e_who = x;
				s.s_thing.b_list = e;
			} else {
				t_bindelem e1 = new t_bindelem();
				t_bindelem e2 = new t_bindelem();
				t_bindlist b = (t_bindlist)pd_new(bindlist_class);
				b.b_list = e1;
				e1.e_who = x;
				e1.e_next = e2;
				e2.e_who = s.s_thing;
				e2.e_next = null;
				s.s_thing = b.b_pd;
			}
		} else
			s.s_thing = x;
	}

	public static void pd_unbind(t_pd x, t_symbol s) {
		if( s.s_thing == x )
			s.s_thing = null;
		else if( s.s_thing && s.s_thing == bindlist_class ) {
			t_bindlist b = (t_bindlist)s.s_thing;
			t_bindelem e, e2;
			e = b.b_list;
			if( e.e_who == x ) {
				b.b_list = e.e_next;
			} else {
				for(e = b.b_list; e2 = e.e_next; e = e2) {
					if( e2.e_who == x ) {
						e.e_next = e2.e_next;
						break;
					}
				}
			}

			if( b.b_list.e_next == null ) {
				s.s_thing = b.b_list.e_who;
				pd_free(b.b_pd);
			}
		} else
			pd_error(x, s.s_name + ": couldn't unbind");
	}

	public static void zz() {}

	public static t_pd pd_findbyclass(t_symbol s, t_class c) {
		t_pd x = null;
		if( s.s_thing == null )
			return null;
		if( s.s_thing == c )
			return s.s_thing;
		if( s.s_thing == bindlist_class ) {
			t_bindlist b = (t_bindlist)s.s_thing;
			t_bindelem e, e2;
			boolean warned = false;
			for(e = b.b_list; e; e = e.e_next)
				if(e.e_who == c) {
					if(x != null and !warned) {
						zz();
						post("warning: " + s.s_name + ": multiply defined");
						warned = true;
					}
					x = e.e_who;
				}
		}
		return x;
	}

	class t_gstack {
		t_pd g_what;
		t_symbol g_loadingabstraction;
		t_gstack g_next;
	}

	public static t_gstack gstack_head = null;
	public static t_pd lastpopped = null;
	public static t_symbol pd_loadingabstraction = null;

	public static int pd_setloadingabstraction(t_symbol sym) {
		t_gstack foo;
		for(foo = gstack_head; foo; foo = foo.g_next)
			if(foo.g_loadingabstraction == sym)
				return 1;
		pd_loadingabstraction = sym;
		return 0;
	}

	public static void pd_pushsym(t_pd x) {
		t_gstack y = new t_gstack();
		y.g_what = s__X.s_thing; //NOTE: what is s__X?
		y.g_next = gstack_head;
		y.g_loadingabstraction = pd_loadingabstraction;
		pd_loadingabstraction = null;
		gstack_head = y;
		s__X.s_thing = x;
	}

	public static void pd_popsym(t_pd x) {
		if( gstack_head == null || s__X.s_thing != x )
			bug("gstack_pop");
		else {
			t_gstack headwas = gstack_head;
			s__X.s_thing = headwas.g_what;
			gstack_head = headwas.g_next;
			lastpopped = x;
		}
	}

	public static void pd_doloadbang() {
		if( lastpopped )
			pd_vmess(lastpopped, gensym("loadbang"), "");
		lastpopped = null;
	}

	public static void pd_bang(t_pd x) {
		x.c_bangmethod.eval(x);
	}

	public static void pd_float(t_px x, float f) {
		x.c_floatmethod.eval(x, f);
	}

	public static void pd_pointer(t_pd x, t_gpointer gp) {
		x.c_pointermethod.eval(x, gp);
	}

	public static void pd_symbol(t_pd x, t_symbol s) {
		x.c_symbolmethod.eval(x, s);
	}

	public static void pd_list(t_pd x, t_symbol s, int argc, t_atom[] argv) {
		x.c_listmethod.eval(x, s, argc, argv);
	}

	public static void pd_init() {
		mess_init();
		obj_init();
		conf_init();
		glob_init();
		garray_init();
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
