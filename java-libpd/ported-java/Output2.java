//AT:
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

	final static int HASHSIZE = 1024;
	static t_symbol[] symhash = new t_symbol[HASHSIZE];
	final static int MAXOBJDEPTH = 1000;
	static int tryingalready;
	static t_pd newest = null;

	class t_symbol {
		String s_name;
		Object s_thing;
		t_symbol s_next;

		public t_symbol(String n, Object t, t_symbol s) {
			s_name  = n;
			s_thing = t;
			s_next  = s;
		}
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
	public static t_symbol class_loadsym;
	public static t_pd pd_objectmaker;
	public static t_pd pd_canvasmaker;
	public static t_symbol class_extern_dir = s_; //TODO: s_ defined?

	public class pd_defaultanything implements Function4<void,t_pd,t_symbol,int,t_atom[]> {
		public void eval(t_pd x, t_symbol s, int argc, t_atom[] argv) {
			pd_error(x, x.c_name.s_name + ": no method for '" + s.s_name + "'");
		}
	}

	public class pd_defaultbang implements Function1<void,t_pd> {
		public void eval(t_pd x) {
			if( !(x.c_listmethod instanceof pd_defaultlist) )
				x.c_listmethod.eval(x, 0, 0, 0);
			else
				x.c_anymethod.eval(x, s_bang, 0, 0)
		}
	}

	public class pd_defaultpointer implements Function2<void,t_pd,t_gpointer> {
		public void eval(t_pd x, t_gpointer gp) {
			t_atom at;
			SETPOINTER(at, gp);
			if( !(x.c_listmethod instanceof pd_defaultlist) )
				x.c_listmethod.eval(x, 0, 1, at);
			else
				x.c_anymethod.eval(x, s_pointer, 1, at);
		}
	}

	public class pd_defaultfloat implements Function2<void,t_pd,t_float> {
		public void eval(t_pd x, t_float f) {
			t_atom at;
			SETFLOAT(at, f);
			if( !(x.c_listmethod instanceof pd_defaultlist) )
				x.c_listmethod.eval(x, 0, 1, at);
			else
				x.c_anymethod.eval(x, s_float, 1, at);
		}
	}

	public class pd_defaultsymbol implements Function2<void,t_pd,t_symbol> {
		public void eval(t_pd x, t_symbol s) {
			t_atom at;
			SETSYMBOL(at, s);
			if( !(x.c_listmethod instanceof pd_defaultlist) )
				x.c_listmethod.eval(x, 0, 1, at);
			else
				x.c_anymethod.eval(x, s_symbol, 1, at);
		}
	}

	//public void obj_list(t_object x, t_symbol s, int argc, t_atom[] argv) {}
	//public static void class_nosavefn(t_gobj z, t_binbuf b) {}
	public class pd_defaultlist implements Function4<void,t_pd,t_symbol,int,t_atom[]> {
		public void eval(t_pd x, t_symbol s, int argc, t_atom[] argv) {
			if( argc == 0 && !(x.c_bangmethod instance_of pd_defaultbang) ) {
				x.c_bangmethod.eval(x);
				return;
			}

			if( argc == 1 ) {
				if( argv[0].a_type == A_FLOAT && !(x.c_floatmethod instance_of pd_defaultfloat) ) {
					x.c_floatmethod.eval(x, argv[0].a_w.w_float);
					return;
				} else if( argv[0].a_type == A_SYMBOL && !(x.c_symbolmethod instance_of pd_defaultsymbol) ) {
					x.c_symbolmethod.eval(x, argv[0].a_w.w_symbol);
					return;
				} else if( argv[0].a_type == A_POINTER && !(x.c_pointermethod instance_of pd_defaultpointer) ) {
					x.c_pointermethod.eval(x, argv[0].a_w.w_gpointer);
					return;
				}
			}

			if( !(x.c_anymethod instance_of pd_defaultanything) )
				x.c_anymethod.eval(x, s_list, argc, argv);
			else if( x.c_patchable )
				obj_list((t_object)x, s, argc, argv);
			else
				pd_defaultanything.eval(x, s_list, argc, argv);
		}
	}

	public static t_class class_new(t_symbol s, t_newmethod newmethod, t_method freemethod,
		int flags, t_atomtype ... type1) {
		t_class c = new t_class();
		int count = 0;
		t_atomtype[] vec = new t_atomtype[MAXPDARG];
		int typeflag = flags & CLASS_TYPEMASK;
		if(!typeflag)
			typeflag = CLASS_PATCHABLE;

		while(true) {
			if(count >= MAXPDARG) {
				error("class " + s.s_name + ": sorry: only " + MAXPDARG + " args typechecked; use A_GIMME");
				break;
			}
			if(count < type1.length)
				vec[count] = type1[count];
			else
				vec[count] = null;
			count++;
		}

		if(pd_objectmaker && newmethod) {
			class_addmethod(pd_objectmaker, newmethod, s, vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
			if(class_loadsym) {
				String loadstring = class_loadsym.s_name;
				if(loadstring.length() > s.s_name.length() &&
					loadstring.substring(loadstring.length() - s.s_name.length()).equals(s.s_name))
					class_addmethod(pd_objectmaker, newmethod, class_loadsym,
						vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
			}
		}

		c.c_helpname = s;
		c.c_name = s;
		c.c_size = 0;
		c.c_methods = null;
		c.c_nmethod = 0;
		c.c_freemethod = freemethod;
		c.c_bangmethod = pd_defaultbang;
		c.c_pointermethod = pd_defaultpointer;
		c.c_floatmethod = pd_defaultfloat;
		c.c_symbolmethod = pd_defaultsymbol;
		c.c_listmethod = pd_defaultlist;
		c.c_anymethod = pd_defaultanything;
		c.c_wb = (typeflag == CLASS_PATCHABLE ? text_widgetbehavior : 0);
		c.c_pwb = 0;
		c.c_firstin = ((flags & CLASS_NOINLET) == 0);
		c.c_patchable = (typeflag == CLASS_PATCHABLE);
		c.c_gobj = (typeflag >= CLASS_GOBJ);
		c.c_drawcommand = 0;
		c.c_floatsignalin = 0;
		c.c_externdir = class_extern_dir;
		c.c_savefn = (typeflag == CLASS_PATCHABLE ? text_save : class_nosavefn);
		return c;
	}

	public static void class_addcreator(t_newmethod newmethod, t_symbol s, t_atomtype ... type1) {
		int count = 0;
		t_atomtype[] vec = new t_atomtype[MAXPDARG];
		while(true) {
			if(count >= MAXPDARG) {
				error("class " + s.s_name + ": sorry: only " + MAXPDARG + " args typechecked; use A_GIMME");
				break;
			}
			if(count < type1.length)
				vec[count] = type1[count];
			else
				vec[count] = null;
			count++;
		}
		class_addmethod(pd_objectmaker, newmethod, s, vec[0], vec[1], vec[2], vec[3], vec[4], vec[5]);
	}

	public static void class_addmethod(t_class c, t_method fn, t_symbol sel, t_atomtype ... arg1) {
		boolean bug = false;
		t_atomtype argtype = (arg1.length > 0 ? arg1[0] : null);

		if(sel == s_signal) {
			if(c.c_floatsignalin)
				post("warning: signal method overrides class_mainsignalin");
			c.c_floatsignalin = -1;
		}

		if(sel == s_bang) {
			if(argtype == null) {
				class_addbang(c, fn);
				return;
			}
		} else if(sel == s_float) {
			if(!(argtype != A_FLOAT || (arg1.length > 1 && arg1[1] != null))) {
				class_doaddfloat(c, fn);
				return;
			}
		} else if(sel == s_symbol) {
			if(!(argtype != A_SYMBOL || (arg1.length > 1 && arg1[1] != null))) {
				class_doaddfloat;
				return;
			}
		} else if(sel == s_list) {
			if(argtype == A_GIMME) {
				class_addlist(c, fn);
				return;
			}
		} else if(sel == s_anything) {
			if(argtype == A_GIMME) {
				class_addanything(c, fn);
				return;
			}
		} else {
			for(int i=0; i<c.c_nmethod; i++) {
				if(c.c_methods[i].me_name == sel) {
					String nbuf = sel.s_name + "_aliased";
					c.c_methods[i].me_name = gensym(nbuf);
					if(c == pd_objectmaker)
						post("warning: class '" + sel.s_name + "' overwritten; old one renamed '" + nbuf + "'");
					else
						post("warning: old method '" + sel.s_name + "' for class '" + c.c_name.s_name + "' renamed '" + nbuf + "'");
				}
			}

			t_methodentry[] methods = new t_methodentry[c.c_nmethod+1];
			t_methodentry m = new t_methodentry();
			for(int i=0; i<c_nmethod; i++)
				methods[i] = c.c_methods[i];
			methods[c_nmethod] = m;
			c.c_nmethod++;
			m.me_name = sel;
			m.me_fun = (t_gotfun)fn;
			m.me_arg = new t_atomtype[(arg1.length < MAXPDARG ? arg1.length : MAXPDARG)+1];

			nargs = 0;
			for(; nargs<arg1.length; nargs++) {
				m.me_arg[nargs] = arg1[nargs];
				if(nargs >= MAXPDARG) {
					error(c.c_name.s_name + "_" + sel.s_name + ": only " + MAXPDARG + " arguments are typecheckable; use A_GIMME");
				}
			}
			m.me_arg[m.me_arg.length] = A_NULL;
			return;
		}

		bug("class_addmethod: " + c.c_name.s_name + "_" + sel.s_name + ": bad argument types\n");
	}

	public static void class_addbang(t_class c, t_method fn) {
		c.c_bangmethod = fn;
	}

	public static void class_addpointer(t_class c, t_method fn) {
		c.c_pointermethod = fn;
	}

	public static void class_doaddfloat(t_class c, t_method fn) {
		c.c_floatmethod = fn;
	}

	public static void class_addsymbol(t_class c, t_method fn) {
		c.c_symbolmethod = fn;
	}

	public static void class_addlist(t_class c, t_method fn) {
		c.c_listmethod = fn;
	}

	public static void class_addanything(t_class c, t_method fn) {
		c.c_anymethod = fn;
	}

	public static void class_setwidget(t_class c, t_widgetbehavior w) {
		c.c_wb = w;
	}

	public static void class_setparentwidget(t_class c, t_parentwidgetbehavior pw) {
		c.c_pwb = pw;
	}

	public static String class_getname(t_class c) {
		return c.c_name.s_name;
	}

	public static String class_gethelpname(t_class c) {
		return c.c_helpname.s_name;
	}

	public static void class_sethelpsymbol(t_class c, t_symbol s) {
		c.c_helpname = s;
	}

	public static t_parentwidgetbehavior pd_getparentwidget(t_pd x) {
		return x.c_pwb;
	}

	public static void class_setdrawcommand(t_class t) {
		c.c_drawcommand = 1;
	}

	public static int class_isdrawcommand(t_class c) {
		return c.c_drawcommand;
	}

	public static void pd_floatforsignal(t_pd x, t_float f) {
		int offset = x.c_floatsignalin;
		if(offset > 0)
			//TODO: this needs to be added to t_class, or another solution can be found
			// Code at m_class.c:419
			x.c_floatforsignal = f;
		else
			pd_error(x.c_name.s_name + ": float unexpected for signal input");
	}

	public static void class_domainsignalin(t_class c, int onset) {
		if(onset <= 0)
			onset = -1;
		else {
			if(!(c.c_floatmethod instance_of pd_defaultfloat))
				post("warning: " + c.c_name.s_name + ": float method overwritten");
			c.c_floatmethod = (t_floatmethod)pd_floatforsignal;
		}
		c.c_floatsignalin = onset;
	}

	public static void class_set_extern_dir(t_symbol s) {
		class_extern_dir = s;
	}

	public static String class_gethelpdir(t_class c) {
		return c.c_externdir.s_name;
	}

	public static void class_nosavefn(t_gobj z, t_binbuf b) {
		bug("save function called but not defined");
	}

	public static class_setsavefn(t_class c, t_savefn f) {
		c->c_savefn = f;
	}

	public static t_savefn class_getsavefn(t_class c) {
		return c.c_savefn;
	}

	public static void class_setpropertiesfn(t_class c, t_propertiesfn f) {
		c.c_propertiesfn = f;
	}

	public static t_propertiesfn class_getpropertiesfn(t_class c) {
		return c.c_propertiesfn;
	}

	public static t_symbol dogensym(String s, t_symbol oldsym) {
		t_symbol sym1, sym2, sym1_prev, sym2_prev;
		unsigned int hash1 = 0, hash2 = 0;
		for(int i=0; i<s.length(); i++) {
			hash1 += (int)s.charAt(i);
			hash2 += hash1;
		}

		sym1_prev = null;
		sym1 = symhash[hash2 & (HASHSIZE-1)];
		while(sym1 != null) {
			sym2_prev = sym1_prev;
			sym2 = sym1;
			if(sym2.s_name.equals(s))
				return sym2;
			sym1_prev = sym1;
			sym1 = sym1.s_next;
		}

		if(oldsym != null) {
			sym2 = oldsym;
			sym2_prev.s_next = sym2;
		} else {
			sym2 = new t_symbol();
			sym2_prev.s_next = sym2;
			sym2.s_name = s;
			sym2.s_next = null;
			sym2.s_thing = null;
		}
		sym1_prev.s_next = sym2;
		return sym2;
	}

	public static t_symbol gensym(String s) {
		return dogensym(s, null);
	}

	public static t_symbol addfileextent(t_symbol s) {
		if(s.s_name.substring(s.s_name.length()-3).equals(".pd"))
			return s;
		else
			return gensym(s.s_name + ".pd");
	}

	public static void new_anything(Object dummy, t_symbol s, int argc, t_atom[] argv) {
		if(tryingagain > MAXOBJDEPTH) {
			error("maximum object loading depth " + MAXOBJDEPTH + " reached");
			return;
		}

		newest = null;
		class_loadsym = s;
		if(sys_load_lib(canvas_getcurrent(), s.s_name) {
			tryingalready++;
			typedmess(dummy, s, argc, argv);
			tryingalready--;
			return;
		}

		class_loadsym = null;
		current = s__X.s_thing;
		int fd = canvas_open(canvas_getcurrent(), s.s_name, ".pd", dirbuf, nameptr, MAXPDSTRING, 0);
		if(fd < 0)
			fd = canvas_open(canvas_getcurrent(), s.s_name, ".pat", dirbuf, nameptr, MAXPDSTRING, 0);
		if(fd >= 0) {
			close(fd);
			if(!pd_setloadingabstraction(s)) {
				canvas_setargs(argc, argv);
				binbuf_evalfile(gensym(nameptr), gensym(dirbuf));
				if(s__X.s_thing != current)
					canvas_popabstraction(s__X.s_thing);
				canvas_setargs(0, 0);
			} else
				error(s.s_name + ": can't load abstraction within itself\n");
		} else
			newest = null;
	}

	public static t_symbol s_pointer  = new t_symbol("pointer", null, null);
	public static t_symbol s_float    = new t_symbol("float", null, null);
	public static t_symbol s_symbol   = new t_symbol("symbol", null, null);
	public static t_symbol s_bang     = new t_symbol("bang", null, null);
	public static t_symbol s_list     = new t_symbol("list", null, null);
	public static t_symbol s_anything = new t_symbol("anything", null, null);
	public static t_symbol s_signal   = new t_symbol("signal", null, null);
	public static t_symbol s__N       = new t_symbol("#N", null, null);
	public static t_symbol s__X       = new t_symbol("#X", null, null);
	public static t_symbol s_x        = new t_symbol("x", null, null);
	public static t_symbol s_y        = new t_symbol("y", null, null);
	public static t_symbol s_         = new t_symbol("", null, null);

	public static t_symbol[] symlist = { s_pointer, s_float, s_symbol,
		s_bang, s_list, s_anything, s_signal, s__N, s__X, s_x, s_y, s_ };

	public static void mess_init() {
		if(pd_objectmaker != null)
			return;
		for(int i = symlist.length - 1; i >= 0; i++)
			dogensym(symlist[i].s_name, symlist[i]);			
		pd_objectmaker = class_new(gensym("objectmaker"), 0, 0, CLASS_DEFAULT, A_NULL);
		pd_canvasmaker = class_new(gensym("classmaker"), 0, 0, CLASS_DEFAULT, A_NULL);
		pd_bind(pd_canvasmaker, s__N);
		class_addanything(pd_objectmaker, new_anything);
	}

	public static t_pd pd_newest() {
		return newest;
	}

	public static void pd_typedmess(t_pd x, t_symbol s, int argc, t_atom[] argv) {
		boolean badarg = false;
		t_methodentry m;
		t_atomtype[] wp;
		t_int[] ai = new t_int[MAXPDARG+1];
		t_floatarg[] ad = new t_floatarg[MAXPDARG+1];
		int ap = 0, dp = 0, narg = 0, argvi = 0;
		t_pd bonzo;

		if(s == s_float) {
			if(argc == 0) {
				c.c_floatmethod.eval(x, 0);
				return;
			} else if(argv[0].a_type == A_FLOAT) {
				c.c_floatmethod.eval(x, argv[0].a_w.w_float);
				return;
			} else
				badarg = true;
		}
		if(s == s_bang) {
			c.c_bangmethod.eval(x);
			return;
		}
		if(s == s_list) {
			c.c_listmethod.eval(x, s, argc, argv);
			return;
		}
		if(s == s_symbol) {
			if(argc != 0 && argv[0].a_type == A_SYMBOL)
				c.c_symbolmethod.eval(x, argv[0].a_w.w_symbol);
			else
				c.c_symbolmethod.eval(x, s);
			return;
		}
		
		if(!badarg) {
			for(int i=0; i<c.c_methods.length && !badarg; i++) {
				m = c.c_methods[i];
				if(m.me_name == s) {
					wp = m.me_arg;
					if(wp == A_GIMME) {
						if(x == pd_objectmaker)
							newest = m.me_fun.eval(s, argc, argv);
						else
							m.me_fun.eval(x, s, argc, argv);
						return;
					}
					if(argc > MAXPDARG)
						argc = MAXPDARG;
					if(x != pd_objectmaker) {
						ai[ap++] = x;
						narg++;
					}
					for(int i=0; i<wp.length; i++) {
						switch(wp[i]) {
						case A_POINTER:
							if(argc == 0) {
								badarg = true;
							} else {
								if(argv[argvi].a_type == A_POINTER) {
									ai[ap] = argv[argvi].a_w.w_gpointer;
									argc--;
									argvi++;
								} else {
									badarg = true;
									break;
								}
							}
							narg++;
							ap++;
							break;
						case A_FLOAT:
							if(argc == 0) {
								badarg = true;
								break;
							}
						case A_DEFFLOAT:
							if(argc == 0)
								ad[dp] = 0;
							else {
								if(argv[argvi].a_type == A_FLOAT)
									ad[dp] = argv[argvi].a_w.w_float;
								else {
									badarg = true;
									break;
								}
								argc--;
								argvi++;
							}
							dp++;
							break;
						case A_SYMBOL:
							if(argc == 0) {
								badarg = true;
								break;
							}
						case A_DEFSYM:
							if(argc == 0)
								ai[ap] = s_;
							else {
								if(argv[argvi].a_type == A_SYMBOL)
									ad[ap] = argv[argvi].a_w.w_symbol;
								else if(x == pd_objectmaker && argv.a_type == A_FLOAT && argv[argvi].a_w.w_float == 0)
									ad[ap] = s_;
								else {
									badarg = true;
									break;
								}
								argc--;
								argvi++;
							}
							narg++;
							ap++;
						}
						if(badarg)
							break;
					}

					if(!badarg) {
						switch(narg) {
						case 0:
							bonzo = (t_fun0.eval(m.me_fun)).eval(ad[0], ad[1], ad[2], ad[3], ad[4]);
							break;
						case 1:
							bonzo = (t_fun1.eval(m.me_fun)).eval(ai[0], ad[0], ad[1], ad[2], ad[3],
								ad[4]);
							break;
						case 2:
							bonzo = (t_fun2.eval(m.me_fun)).eval(ai[0], ai[1], ad[0], ad[1], ad[2],
								ad[3], ad[4]);
							break;
						case 3:
							bonzo = (t_fun3.eval(m.me_fun)).eval(ai[0], ai[1], ai[2], ad[0], ad[1],
								ad[2], ad[3], ad[4]);
							break;
						case 4:
							bonzo = (t_fun4.eval(m.me_fun)).eval(ai[0], ai[1], ai[2], ai[3], ad[0],
								ad[1], ad[2], ad[3], ad[4]);
							break;
						case 5:
							bonzo = (t_fun5.eval(m.me_fun)).eval(ai[0], ai[1], ai[2], ai[3], ai[4],
								ad[0], ad[1], ad[2], ad[3], ad[4]);
							break;
						case 6:
							bonzo = (t_fun6.eval(m.me_fun)).eval(ai[0], ai[1], ai[2], ai[3], ai[4],
								ai[5], ad[0], ad[1], ad[2], ad[3], ad[4]);
							break;
						default:
							bonzo = null;
							break;
						}
						if(x == pd_objectmaker)
							newest = bonzo;
						return;
					}
				}
			}
		}

		if(!badarg)
			c.c_anymethod(x, s, argc, argv);
		else
			pd_error(x, "Bad arguments for message '" + s.s_name + "' to object '" + c.c_name.s_name + "'");
	}

	public static void pd_vmess(t_pd x, t_symbol sel, char ... fmt) {
		t_atom[] arg = new t_atom[10];
		boolean done = false;

		int i;
		for(i=0; i<fmt.length; i++) {
			if( i >= 10 ) {
				pd_error(x, "pd_vmess: only 10 allowed");
				break;
			}

			switch(fmt[i]) {
			case 'f':
				arg[i] = new t_atom();
				SETFLOAT(arg[i], (float)fmt[i]);
				break;
			case 's':
				arg[i] = new t_atom();
				SETSYMBOL(arg[i], (t_symbol)fmt[i]);
				break;
			case 'i':
				arg[i] = new t_atom();
				SETFLOAT(arg[i], (t_int)fmt[i]);
				break;
			case 'p':
				arg[i] = new t_atom();
				SETPOINTER(arg[i], (t_gpointer)fmt[i]);
				break;
			default:
				done = true;
				break;
			}

			if( done )
				break;
		}
		typedmess(x, sel, i, arg);
	}

	public static void pd_forwardmess(t_pd x, int argc, t_atom[] argv) {
		if(argc != 0) {
			t_atomtype t = argv[0].a_type;
			if(t == A_SYMBOL)
				pd_typedmess(x, argv[0].a_w.w_symbol, argc-1, Arrays.copyOfRange(argv, 1, argv.length));
			else if(t == A_POINTER) {
				if(argc == 1)
					pd_pointer(x, argv[0].a_w.w_gpointer);
				else
					pd_list(x, s_list, argc, argv);
			} else if(t == A_FLOAT) {
				if(argc == 1)
					pd_float(x, argv[0].a_w.w_float);
				else
					pd_list(x, s_list, argc, argv);
			} else
				bug("pd_forwardmess");
		}
	}

	class nullfn implements Function0<void> {
		public void eval() {
		}
	}

	public static t_gotfn getfn(t_pd x, t_symbol s) {
		for(int i = x.c_methods.length - 1; i >= 0; i--)
			if(x.c_methods[i].me_name == s)
				return x.c_methods[i].me_fun;
		pd_error(x, x.c_name.s_name + ": no method for message '" + s.s_name + "'");
		return new nullfn();
	}

	public static t_gotfn zgetfn(t_pd x, t_symbol s) {
		for(int i = x.c_methods.length - 1; i >= 0; i--)
			if(x.c_methods[i].me_name == s)
				return x.c_methods[i].me_name;
		return null;
	}

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
		x.c_listmethod.eval(x, s_list, argc, argv); //NOTE: s_list OR s ??
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
