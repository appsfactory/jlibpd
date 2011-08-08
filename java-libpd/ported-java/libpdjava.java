/*
 s.main	Line 24   //not sure of defining size of NFONT

Not sure: if (sys_argparse (argc - 1 , argv[1] ))
argv[1]? 


sys_findprogdir not done yet in s.main


sys_parsedevlist . str = endp + 1?


define: sys_messagelist :: line 659
 
Incomplete: static void sys_afterargparse(void)
(last function)
 
 */


import java.util.ArrayList;
import java.util.Collections; 
import java.util.List;
import java.util.Arrays;

public class libpdjava {
	public static int goton = 0;
	static int sys_mmio = 0;
	static int sys_nmidiin = -1;
	public static int id_usage = 1;
	static int sys_version;
	static int sys_debuglevel;
	static int sys_nogui;
	static int sys_guisetportnumber;
	static int sys_noloadbang;
	static int sys_hipriority = -1;
	static int sys_extraflags;
	static String sys_guicmd;
	static int sys_nosleep=0;
	 static int sys_nsoundin = -1;
	 static int sys_nsoundout = -1;
	 static int[] sys_soundindevlist = new int[4];
	 static int[] sys_soundoutdevlist = new int[4];
	 static int sys_midiindevlist[] = {1};
	 static int sys_midioutdevlist[] = {1};
	 static int sys_nchin = -1;
	 static int sys_nchout = -1;
	 static int[] sys_chinlist = new int[4];
	 static int[] sys_choutlist = new int[4];
	 
	 static int sys_main_srate;
	 static int sys_main_advance;
	 static int sys_main_callback;
	 static int sys_main_blocksize;
	 static int sys_listplease;
	 static int sys_batch;
	 
	 //not sure of defining size of NFONT
	 static int NFONT = 4;
	 
	public static void main (String[] args)
	{
		
		int sys_debuglevel;
		int sys_verbose;
		int sys_noloadbang;
		int sys_nogui;
		int sys_hipriority = -1;    /* -1 = don't care; 0 = no; 1 = yes */
		int sys_guisetportnumber;   /* if started from the GUI, this is the port # */
		int sys_nosleep = 0;  /* skip all "sleep" calls and spin instead */
		char sys_guicmd;
		int sys_oldtclversion;      /* hack to warn g_rtext.c about old text sel */

		int sys_nmidiout = -1;
		int sys_nmidiin = -1;
		 int[] sys_midiindevlist = new int[] {1};
			
		 int[] sys_midioutdevlist = new int[] {1};

		//j   //j  #ifdef __APPLE__
		 String sys_font =  "Monaco";
		 String sys_fontweight = "normal";
		//j   #else
		//j public String sys_font = "Courier";
		//j public String sys_fontweight = "bold";
		//j   //j  #endif
		

		 int sys_externalschedlib;
		 String sys_externalschedlibname;
		 int sys_extraflags;
		 String sys_extraflagsstring;
		 
		 //forward declaration not needed
		 //int sys_run_scheduler(final String externalschedlibname, final String sys_extraflagsstring);
		 
		 int sys_noautopatch;    /* temporary hack to defeat new 0.42 editing */

		    /* here the "-1" counts signify that the corresponding vector hasn't been
		    specified in command line arguments; sys_set_audio_settings will detect it
		    and fill things in. */
		 

		 class Fontinfo {
		 	
		 	  int fi_fontsize;
		 	    int fi_maxwidth;
		 	    int fi_maxheight;
		 	    int fi_hostfontsize;
		 	    int fi_width;
		 	    int fi_height;
		 	    
		 	    Fontinfo (int a, int b, int c, int d, int e, int f)
		 	    {
		 		  fi_fontsize = a;
		 		  fi_maxwidth = b;
		 		  fi_maxheight = c;
		 		  fi_hostfontsize = d;
		 		  fi_width = e;
		 		  fi_height = f;
		 	    }

		 }
		 
		 
		 Fontinfo sys_fontlist[] = new Fontinfo[4];
			sys_fontlist[0] = new Fontinfo(8, 6, 10, 0, 0, 0);
			sys_fontlist[1] = new Fontinfo(8, 6, 10, 0, 0, 0);
			sys_fontlist[2] = new Fontinfo(8, 6, 10, 0, 0, 0);
			sys_fontlist[3] = new Fontinfo(8, 6, 10, 0, 0, 0);
		 
		 
		System.out.println("Hellos");
		
		
	}
	
	public static int get_sys_main_advance ()  {
		
		return sys_main_advance ;
		
	
	}

public static double get_sys_time_per_dsp_tick () {
		return sys_time_per_dsp_tick ; //in m_sched.c
}
public static int get_sys_schedblocksize () {

	return sys_schedblocksize ;		//in m_sched.c
	
}

public static double get_sys_time () {
	return sys_time ;	//in m_sched.c

}

public static int get_sys_sleepgrain () {
	return sys_sleepgrain ;			//in m_sched.c
	
}

public static int get_sys_schedadvance ()  {

	return sys_schedadvance ;   //in m_sched.c

}


public static EOF sys_findfont (int fontsize) throws Exception {
return 1; //dummy 
	}

public static int sys_nearestfontsize (int fontsize) throws Exception {
	return 1;
	}

public static int sys_hostfontsize (int fontsize) throws Exception {
	return 1;
	}

public static int sys_fontwidth (int fontsize) throws Exception {
	return 1;
	}

public static int sys_fontheight (int fontsize) throws Exception {
	return 1;
	}

public static void openit (String dirname, String filename) throws Exception {
	try {
		String dirbuf;
		String nameptr;
		int fd = open_via_path (dirname , filename , "" , dirbuf , nameptr , 1000 , 0 );
		if (fd >= 0 ) {
			close (fd );				/// JAVA Equivalent of close?
			glob_evalfile (0 , gensym (nameptr ), gensym (dirbuf ));
			}
		else error ("%s: can't open" , filename );
		}
	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
		}
	}



public static int sys_main (int argc, String[] argv) throws Exception {
	try {
		int i;
		int noprefs;
		sys_externalschedlib = 0 ;
		sys_extraflags = 0 ;
		//fprintf (stderr , "Pd: COMPILED FOR DEBUGGING\n" );
		pd_init ();
		sys_findprogdir (argv [0 ]);
		for (i = noprefs = 0 ; i < argc ; i ++ ) 
			if (argv [i].equals("-noprefs" )) 
				noprefs = 1;
		if (noprefs == 0 ) 
			sys_loadpreferences ();
		if (noprefs == 0 ) 
			sys_rcfile ();
		if (sys_argparse (argc - 1 , argv[1] )) 
			return 1;
		sys_afterargparse ();
		if ((sys_verbose )|| (sys_version )) 
			System.err (stderr , "%s compiled %s %s\n" , pd_version , pd_compiletime , pd_compiledate );
		if (sys_version ) 
			return 0;
		if (sys_startgui (sys_libdir (s_name ))) 
			return 1;
		if (sys_externalschedlib ) 
			return sys_run_scheduler (sys_externalschedlibname , sys_extraflagsstring );
		else if (sys_batch ) 
			return m_batchmain ();
		else {
			sys_reopen_midi ();
			sys_reopen_audio ();
			return m_mainloop ();
			}
		}
	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
		}
	}

// CHECK IF sys_parsedevlist is fine?
public static int sys_parsedevlist (int np, int[] vecp, int max, String str) throws Exception {
	try {
		int n = 0 ;
		while (n < max) {
			if (str == null ) 
				break;
			else {
				String endp;
				vecp [n ]= Integer.parseInt(str);
				if (endp.equals(str)) 
					break; 
				n++ ;
				if (endp == null) 
					break;
				str = endp + 1 ;
				}
			}
		
		return n; 
		}
	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
		}
	}


public static int sys_getmultidevchannels (int n, int[] devlist) throws Exception {
	try {
		int sum = 0 ;
		if (n < 0 ) return -1; 
		if (n == 0 ) return 0;
		while (n-- !=0 )
			sum = sum + devlist[0]++ ;
		
		return sum ;
		}
	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
		}
	}





public static void sys_findprogdir (String progname) throws Exception {
	try {
		String sbuf;
		String sbuf2;
		String sp;
		String lastslash;
		//   struct stat statbuf;  //not in java yet
		
		GetModuleFileName (null , sbuf2 , sbuf2.length() );
		sys_unbashfilename (sbuf2 , sbuf );
		sbuf = progname;
		
		/*

		lastslash = strrchr (sbuf , '/' );
		if (lastslash ) {
			lastslash [0] = 0 ;
			lastslash = strrchr (sbuf , '/' );
			if (lastslash ) {
				strncpy (sbuf2 , sbuf , lastslash - sbuf );
				sbuf2 [lastslash - sbuf ]= 0 ;
				}
			else strcpy (sbuf2 , ".." );
			}
		else {
			strcpy (sbuf2 , INSTALL_PREFIX );
			strcpy (sbuf2 , "." );
			}
		sys_libdir = gensym (sbuf2 );
		strncpy (sbuf , sbuf2 , MAXPDSTRING - 30 );
		sbuf [MAXPDSTRING - 30 ]= 0 ;
		strcat (sbuf , "/lib/pd" );
		if (stat (sbuf , &statbuf )>= 0 ) {
			sys_libdir = gensym (sbuf );
			}
		else {
			sys_libdir = gensym (sbuf2 );
			}
		*/	
		}
	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
	}
		
	}



public static int sys_argparse (int argc, String[] argv) throws Exception {
	try {
		
	
		
		
		String sbuf;
		int i;
		while ((argc > 0 )&& (argv[0] == "-"))
		{
			
			
			
			if ( (argv[0].equals("-r")) && (argc > 1 ) ) {
			if ( (argv[1].split(" ", 1))[0].matches("%d") )
			{
				sys_main_srate = Integer.parseInt((argv[1].split(" ", 1))[0]);
			
					argc = argc - 2 ;
				  
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					
					/*
					 ArrayList list = new ArrayList();
					 list.addAll(Arrays.asList(argv));
					 list.remove(0);
					 list.remove(0);
					 
					 argv = (String[])list.toArray();
					 */
					
					}
			
			
		}
				else if ( (argv[0].equals("-inchannels")) && (argc > 1 ) ) {
					sys_nchin = sys_parsedevlist (sys_nchin , sys_chinlist , 4 , argv [1 ]);
					if (sys_nchin ==0  ) {
						goton = id_usage;
					}
					else {
				
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					
					}
					}
				else if ( (argv[0].equals("-outchannels")) && (argc > 1 ) ) {
					sys_parsedevlist (sys_nchout , sys_choutlist , 4 , argv [1 ]);
					if (sys_nchout == 0 ) 
					{
						goton = id_usage;
						
					}
					else {
						
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
						
						}
					}
				else if ( (argv[0].equals("-channels")) && (argc > 1 ) ) {
					sys_parsedevlist (sys_nchin , sys_chinlist , 4 , argv [1 ]);
					sys_parsedevlist (sys_nchout , sys_choutlist , 4 , argv [1 ]);
					if (sys_nchout ==0 ){ 
						goton = id_usage;}
					else {
						
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
						
						}
					}
				else if ( (argv[0].equals("-soundbuf")) || (argv[0].equals("-audiobuf")) && (argc > 1 ) ) {
					sys_main_advance = Integer.parseInt(argv[1]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-callback")){
					sys_main_callback = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-blocksize")) {
					sys_main_blocksize = Integer.parseInt(argv[1]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if ( (argv[0].equals("-sleepgrain")) && (argc > 1 ) ) {
					sys_sleepgrain = 1000 * Double.parseDouble(argv[1]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-nodac")) {
					sys_nsoundout = 0 ;
					sys_nchout = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-noadc")) {
					sys_nsoundin = 0 ;
					sys_nchin = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ( (argv[0].equals("-nosound")) || (argv[0].equals("-noaudio")) ) {
					sys_nsoundin = sys_nsoundout = 0 ;
					sys_nchin = sys_nchout = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-oss")) {
					sys_set_audio_api (API_OSS );
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-alsa")) {
					sys_set_audio_api (API_ALSA );
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ( (argv[0].equals("-alsoadd")) && (argc > 1 ) ) {
					if (argc > 1 ) 
					{
						alsa_adddev (argv [1 ]);
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
						
					}
					else {
					
						goton = id_usage;
					}
				
					}
				else if (argv[0].equals("-alsamidi")) {
					sys_set_midi_api (API_ALSA );
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-jack")) {
					sys_set_audio_api (API_JACK );
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
			
			
				else if ((argv[0].equals("-pa")) || (argv[0].equals("-portaudio")) || (argv[0].equals("-asio")) ) {
					sys_set_audio_api (API_PORTAUDIO );
					sys_mmio = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-mmio")) {
					sys_set_audio_api (API_MMIO );
					sys_mmio = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-audiounit")) {
					sys_set_audio_api (API_AUDIOUNIT );
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-esd")) {
					sys_set_audio_api (API_ESD );
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-nomidiin")) {
					sys_nmidiin = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-nomidiout")) {
					sys_nmidiout = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-nomidi")) {
					sys_nmidiin = sys_nmidiout = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-midiindev")) {
					sys_nmidiin = sys_parsedevlist (sys_nmidiin , sys_midiindevlist , 16 , argv [1 ]);
					if (sys_nmidiin == 0) 
					{
						goton = id_usage;
					}
					
					else {
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);	
						
					}
					}
			
				else if ( (argv[0].equals("-midioutdev")) && (argc > 1 ) ) {
					sys_parsedevlist (sys_nmidiout , sys_midioutdevlist , 16 , argv [1 ]);
					if ( sys_nmidiout == 0 ) goton = id_usage;
					else {
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
					}
				else if ( (argv[0].equals("-mididev")) && (argc > 1 ) ) {
					sys_parsedevlist (sys_nmidiin , sys_midiindevlist , 16 , argv [1 ]);
					sys_parsedevlist (sys_nmidiout , sys_midioutdevlist , 16 , argv [1 ]);
					if (sys_nmidiout == 0 ) goton = id_usage;
					else {
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
					}
				else if ( (argv[0].equals("-path")) && (argc > 1 ) ) {
					sys_searchpath = namelist_append_files (sys_searchpath , argv [1 ]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-nostdpath")) {
					sys_usestdpath = 0 ;		//path.c
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-stdpath")) {
					sys_usestdpath = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-helppath")) {
					sys_helppath = namelist_append_files (sys_helppath , argv [1 ]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if ((argv[0].equals("-open")) && (argc > 1 )) {
					sys_openlist = namelist_append_files (sys_openlist , argv [1 ]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if ((argv[0].equals("-lib")) && (argc > 1 )) {
					sys_externlist = namelist_append_files (sys_externlist , argv [1 ]);
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (((argv[0].equals("-font-size")) || (argv[0].equals("-font")) ) && (argc > 1 ))  {
					//dummy
					/*
					 *      strncpy(sys_font,*(argv+1),sizeof(sys_font)-1);
            				sys_font[sizeof(sys_font)-1] = 0;
            
					 */
					
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (((argv[0].equals("-font-face")) || (argv[0].equals("-typeface")) ) && (argc > 1 )) {
					/*
					strncpy (sys_font , argv + 1 [0] , sizeof sys_font - 1 );
					sys_font [sizeof sys_font - 1 ]= 0 ;
					*/
					
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if ((argv[0].equals("-font-weight")) && (argc > 1 )) {
					//dummy
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-verbose")) {
					sys_verbose++;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-version")) {
					sys_version = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ((argv[0].equals("-d")) && (argc > 1 )) {
					
					if ( (argv[1].split(" ", 1))[0].matches("%d") )
					{
						sys_debuglevel = Integer.parseInt((argv[1].split(" ", 1))[0]);
					
					
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
					}
			
			
			
				else if (argv[0].equals("-noloadbang")) {
					sys_noloadbang = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-nogui")) {
					sys_printtostderr = sys_nogui = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ((argv[0].equals("-guiport")) && (argc > 1 ))
				{
					
					if ( (argv[1].split(" ", 1))[0].matches("%d") )
					{
						sys_guisetportnumber = Integer.parseInt((argv[1].split(" ", 1))[0]);
					
					
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
			
					
				
					}
				else if (argv[0].equals("-stderr")) {
					sys_printtostderr = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ((argv[0].equals("-guicmd")) && (argc > 1 )) {
					sys_guicmd = argv [1 ];
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if ((argv[0].equals("-send")) && (argc > 1 )) {
					sys_messagelist = namelist_append (sys_messagelist , argv [1 ], 1 );
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-listdev")) {
					sys_listplease = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ((argv[0].equals("-schedlib")) && (argc > 1 )) {
					sys_externalschedlib = 1 ;
					sys_externalschedlibname = argv[1];
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if ((argv[0].equals("-extraflags")) && (argc > 1 )) {
					sys_extraflags = 1 ;
					sys_extraflagsstring = argv[1];
					argc = argc - 2 ;
					argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
				else if (argv[0].equals("-batch")) {
					sys_batch = 1 ;
					sys_printtostderr = sys_nogui = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-noautopatch")) {
					sys_noautopatch = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ((argv[0].equals("-rt")) || (argv[0].equals("-realtime"))) {
					sys_hipriority = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-nrt")) {
					sys_hipriority = 0 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if (argv[0].equals("-nosleep")) {
					sys_nosleep = 1 ;
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);
					}
				else if ((argv[0].equals("-soundindev")) || (argv[0].equals("-audioindev"))) {
					sys_nsoundin = sys_parsedevlist (sys_nsoundin , sys_soundindevlist , 4 , argv [1 ]);
					if (sys_nsoundin == 0) 
						goton = id_usage;
					else {
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
					}
				else if ((argv[0].equals("-soundoutdev")) || (argv[0].equals("-audiooutdev"))) {
					sys_nsoundout = sys_parsedevlist (sys_nsoundout , sys_soundoutdevlist , 4 , argv [1 ]);
					if (sys_nsoundout == 0 ) 
						goton = id_usage;
					else {
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
					}
				else if ( ( (argv[0].equals("-sounddev")) || (argv[0].equals("-audiodev")) ) && (argc > 1 )) 
				{
					sys_nsoundin = sys_parsedevlist (sys_nsoundin , sys_soundindevlist , 4 , argv [1 ]);
					sys_nsoundout = sys_parsedevlist (sys_nsoundout , sys_soundoutdevlist , 4 , argv [1 ]);
					if (sys_nsoundout == 0) 
						goton = id_usage;
					else {
						argc = argc - 2 ;
						argv = Arrays.copyOfRange(argv, 2, argv.length);
					}
					}
				else if (argv[0].equals("-noprefs")) 
				{
					
					argc = argc - 1 ;
					argv = Arrays.copyOfRange(argv, 1, argv.length);	
				}
				else {
					//dummy no usage method
					}
	} //outside while after this
		
		
while (argc c > 0)		
{
	
    sys_openlist = namelist_append_files(sys_openlist, argv);
	argc = argc - 1 ;
	argv = Arrays.copyOfRange(argv, 1, argv.length);

}

	}

	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
		}
	}
	

public static int sys_getblksize () throws Exception {

	return 64;
	
}





public static void sys_addreferencepath () {
	
		String sbuf;
		
	
	}





















/*

public static void sys_afterargparse ( void ) throws Exception {
	try {
		String sbuf;
		int i;
		int naudioindev;
		int[] audioindev;
		int[] chindev;
		int naudiooutdev;
		int[] audiooutdev;
		int[] choutdev;
		int nchindev;
		int nchoutdev;
		int rate;
		int advance;
		int callback;
		int blocksize;
		int nmidiindev = 0 ;
		int[] midiindev;
		int nmidioutdev = 0 ;
		int[] midioutdev;
		
		sbuf = sys_libdir(s_name);
		sbuf [MAXPDSTRING - 30 ]= 0 ;
		strcat (sbuf , "/extra" );
		sys_setextrapath (sbuf );
		strncpy (sbuf , sys_libdir (s_name ), MAXPDSTRING - 30 );
		sbuf [MAXPDSTRING - 30 ]= 0 ;
		strcat (sbuf , "/doc/5.reference" );
		sys_helppath = namelist_append_files (sys_helppath , sbuf );
		if (!sys_mmio ) {
			for (i = 0 ; i < sys_nsoundin ; i ++ ) sys_soundindevlist i -- ;
			for (i = 0 ; i < sys_nsoundout ; i ++ ) sys_soundoutdevlist i -- ;
			}
		for (i = 0 ; i < sys_nmidiin ; i ++ ) sys_midiindevlist i -- ;
		for (i = 0 ; i < sys_nmidiout ; i ++ ) sys_midioutdevlist i -- ;
		if (sys_listplease ) sys_listdevs ()
		sys_get_audio_params (&naudioindev , audioindev , chindev , &naudiooutdev , audiooutdev , choutdev , &rate , &advance , &callback , &blocksize );
		if (sys_nchin >= 0 ) {
			nchindev = sys_nchin ;
			for (i = 0 ; i < nchindev ; i ++ ) chindev [i ]= sys_chinlist [i ];
			}
		else nchindev = naudioindev ;
		if (sys_nsoundin >= 0 ) {
			naudioindev = sys_nsoundin ;
			for (i = 0 ; i < naudioindev ; i ++ ) audioindev [i ]= sys_soundindevlist [i ];
			}
		if (sys_nchout >= 0 ) {
			nchoutdev = sys_nchout ;
			for (i = 0 ; i < nchoutdev ; i ++ ) choutdev [i ]= sys_choutlist [i ];
			}
		else nchoutdev = naudiooutdev ;
		if (sys_nsoundout >= 0 ) {
			naudiooutdev = sys_nsoundout ;
			for (i = 0 ; i < naudiooutdev ; i ++ ) audiooutdev [i ]= sys_soundoutdevlist [i ];
			}
		sys_get_midi_params (&nmidiindev , midiindev , &nmidioutdev , midioutdev );
		if (sys_nmidiin >= 0 ) {
			post ("sys_nmidiin %d, nmidiindev %d" , sys_nmidiin , nmidiindev );
			nmidiindev = sys_nmidiin ;
			for (i = 0 ; i < nmidiindev ; i ++ ) midiindev [i ]= sys_midiindevlist [i ];
			}
		if (sys_nmidiout >= 0 ) {
			nmidioutdev = sys_nmidiout ;
			for (i = 0 ; i < nmidioutdev ; i ++ ) midioutdev [i ]= sys_midioutdevlist [i ];
			}
		if (sys_main_advance ) advance = sys_main_advance 
		if (sys_main_srate ) rate = sys_main_srate 
		if (sys_main_callback ) callback = sys_main_callback 
		if (sys_main_blocksize ) blocksize = sys_main_blocksize 
		sys_set_audio_settings (naudioindev , audioindev , nchindev , chindev , naudiooutdev , audiooutdev , nchoutdev , choutdev , rate , advance , callback , blocksize );
		sys_open_midi (nmidiindev , midiindev , nmidioutdev , midioutdev , 0 );
		}
	catch(Exception e) {
		if (!(e instanceof GotoException)) {
			throw e;
			}
		}
	}


*/






// d_dac.c





class t_dac {
	  t_object x_obj;
	    t_int x_n;
	    t_int[] x_vec;
	    t_float x_f;
}




public static void dac_new (t_symbol s, int argc, t_atom[] argv)
{
	t_dac x = (t_dac) pd_new(dac_class);
	t_atom[] defarg = new t_atom[2];
	t_atom[] ap;
	int i;
	if (argc == 0){
		
		argv = defarg;
        argc = 2;
        SETFLOAT(defarg[0], 1);
        SETFLOAT(defarg[1], 2);
		
	}
	
	x.x_n = argc;
	x.x_vec= new t_int();
	
	for (i=0; i< argc; i++)
	{
	x.x_vec[i] = atom_getintarg(i, argc, argv);
	for (i=1; i< argc; i++)
	{
		
		inlet_new(x.x_obj, x.x_obj.ob_pd, s_signal, s_signal);
		
	}
	x.x_f=0;
// 	return x;
	}
}




public static void dac_dsp (t_dac x, t_signal[] sp) {
	
	t_int i;
	t_int[] ip;
	t_signal[] sp2;
	for (i=x.x_n, ip=x.x_vec, sp2=sp; i--; ip++, sp2++)
		
	{
		int ch = ip[0] - 1;
		if (sp2[0].s_n != 64)
			error("dac~: bad vector size");
		else if (ch >= 0 && ch < sys_get_outchannels())
		{
            dsp_add(plus_perform, 4, sys_soundout + 64*ch, sp2[0].s_vec, sys_soundout + 64*ch, 64);
			
		}
		
		
	}
}





public static void dac_free(t_dac x)
{
    freebytes(x.x_vec, 64);
	//dummy, dont need dac_free?
}

public static void dac_setup()
{
    dac_class = class_new(gensym("dac~"), (t_newmethod)dac_new,
        (t_method)dac_free, 64, 0, A_GIMME, 0);
    CLASS_MAINSIGNALIN(dac_class, t_dac, x_f);
    class_addmethod(dac_class, (t_method)dac_dsp, gensym("dsp"), A_CANT, 0);
    class_sethelpsymbol(dac_class, gensym("adc~_dac~"));
}



class t_adc {
	  t_object x_obj;
	    t_int x_n;
	    t_int[] x_vec;
	    
}

public static void adc_new (t_symbol s, int argc, t_atom[] argv)
{
	
	t_adc x = (t_adc) pd_new(adc_class);
	t_atom [] defarg = new t_atom[2];
	t_atom[] ap;
	int i;
	if (argc  == 0)
	{
		argv = defarg;
        argc = 2;
        SETFLOAT(defarg[0], 1);
        SETFLOAT(defarg[1], 2);
		
	}
	
	x.x_n = argc;
	x.x_vec= new t_int();
	
	
	for (i=0; i< argc; i++)
	{
	x.x_vec[i] = atom_getintarg(i, argc, argv);
	for (i=1; i< argc; i++)
		{
			outlet_new(x.x_obj, s_signal);
		}
	}
	
	// return x;
	
	
	
}


public static t_int copy_perform(t_int[] w) {
	
	t_sample in1 = (t_sample)(w[1]);
	t_sample out = (t_sample)(w[2]);
	int n = (int)(w[3]);
	while (n--){
		out++ = in1++;
	}
	return (w+4);
}


public static t_int copy_perf8(t_int w)
{
	
	t_sample in1 = (t_sample)(w[1]);
	t_sample out = (t_sample)(w[2]);
	int n = (int)(w[3]);

    for (; n; n -= 8, in1 += 8, out += 8)
    {
    	  t_sample f0 = in1[0];
          t_sample f1 = in1[1];
          t_sample f2 = in1[2];
          t_sample f3 = in1[3];
          t_sample f4 = in1[4];
          t_sample f5 = in1[5];
          t_sample f6 = in1[6];
          t_sample f7 = in1[7];

          out[0] = f0;
          out[1] = f1;
          out[2] = f2;
          out[3] = f3;
          out[4] = f4;
          out[5] = f5;
          out[6] = f6;
          out[7] = f7;
    	
    }
    return (w+4);
	
	
}



public static void dsp_add_copy(t_sample in, t_sample out, int n)
{
	
	 if (n&7)
	        dsp_add(copy_perform, 3, in, out, n);
	    else        
	        dsp_add(copy_perf8, 3, in, out, n);
	
	
}


public static void adc_dsp (t_adc x, t_signal[] sp)
{
	
	t_int i;
	t_int[] ip;
	t_signal[] sp2;
	
    for (i = x.x_n, ip = x.x_vec, sp2 = sp; i--; ip++, sp2++)
    {
        int ch = ip[0] - 1;
        if ((sp2[0]).s_n != 64)
            error("adc~: bad vector size");
        else if (ch >= 0 && ch < sys_get_inchannels())
            dsp_add_copy(sys_soundin + 64*ch, (sp2[0]).s_vec, 64);
        else dsp_add_zero((sp2[0]).s_vec, DEFDACBLKSIZE);
    }   
	
	
}


public static void adc_free (t_adc x)
{
	
	freebytes(x.x_vec, x.x_n * 32); // sizeof operator
	
}



public static void adc_setup()
{
    adc_class = class_new(gensym("adc~"), (t_newmethod)adc_new, (t_method)adc_free, 64, 0, A_GIMME, 0);
    class_addmethod(adc_class, (t_method)adc_dsp, gensym("dsp"), A_CANT, 0);
    class_sethelpsymbol(adc_class, gensym("adc~_dac~"));
}


public static void d_dac_setup() {
	
	dac_setup();
	adc_setup();
}




// d_osc.c, starting from union tabfudge

final static int UNITBIT32 = 1572864;



	class tabfudge { }
	class tabfudge_double extends tabfudge { double tf_d; }
	class tabfudge_int32_t   extends tabfudge { 
		long[] tf_i = new long[2]; }

	

	class t_phasor {
		
		t_object x_obj;
		double x_phase;
		 float x_conv;
		    float x_f;      /* scalar frequency */
		
	}
	
	public static void phasor_new(t_floatarg f)
	{
	    t_phasor x = (t_phasor )pd_new(phasor_class);
	    x.x_f = f;
	    inlet_new(x.x_obj, x.x_obj.ob_pd, s_float, gensym("ft1"));
	    x.x_phase = 0;
	    x.x_conv = 0;
	    outlet_new(x.x_obj, gensym("signal"));
	    return (x);
	}


	public static void t_int phasor_perform (t_int[] w)
	
	{
		
	    t_phasor x = (t_phasor)(w[1]);
	    t_float in = (t_float)(w[2]);
	    t_float out = (t_float)(w[3]);
	    int n = (int)(w[4]);
	    double dphase = x.x_phase + (double)UNITBIT32;
	    tabfudge tf;
	    int normhipart;
	    float conv = x.x_conv;

	    tf.tf_d = UNITBIT32;
	    normhipart = tf.tf_i[HIOFFSET];
	    tf.tf_d = dphase;

	    while (n--)
	    {
	        tf.tf_i[HIOFFSET] = normhipart;
	        dphase += in++ * conv;
	        out++ = tf.tf_d - UNITBIT32;
	        tf.tf_d = dphase;
	    }
	    tf.tf_i[HIOFFSET] = normhipart;
	    x.x_phase = tf.tf_d - UNITBIT32;
	    return (w+5);
		
	}
	
	
	public static void phasor_dsp (t_phasor x, t_signal[] sp)
	
	{
		x.x_conv = 1./sp[0].s_sr;
	    dsp_add(phasor_perform, 4, x, sp[0].s_vec, sp[1].s_vec, sp[0].s_n);

	}
	
	public static void phasor_ft1(t_phasor x, t_float f)
	{
	    x.x_phase = f;
	}

	
	
	public static void phasor_setup()
	{
	    phasor_class = class_new(gensym("phasor~"), (t_newmethod)phasor_new, 0,64, 0, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(phasor_class, t_phasor, x_f);
	    class_addmethod(phasor_class, (t_method)phasor_dsp, gensym("dsp"), 0);
	    class_addmethod(phasor_class, (t_method)phasor_ft1, gensym("ft1"), A_FLOAT, 0);
	}
	
	
	class t_cos {
		t_object x_obj;
		float x_f;
	}
	
	
	public static void cos_new()
	{
	    t_cos x = (t_cos )pd_new(cos_class);
	    outlet_new(x.x_obj, gensym("signal"));
	    x.x_f = 0;
	    return (x);
	}
	
	
	
	
	
	
	
	
	public static t_int cos_perform(t_int[] w)
	{
	    t_float in = (t_float )(w[1]);
	    t_float out = (t_float )(w[2]);
	    int n = (int)(w[3]);
	    float tab = cos_table;
	    float [] addr;
	    float f1, f2, frac;
	    double dphase;
	    int normhipart;
	    tabfudge tf;
	    
	    tf.tf_d = UNITBIT32;
	    normhipart = tf.tf_i[HIOFFSET];
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    dphase = (double)(in++ * (float)(COSTABSIZE)) + UNITBIT32;
        tf.tf_d = dphase;
        addr = tab + (tf.tf_i[HIOFFSET] & (COSTABSIZE-1));
        tf.tf_i[HIOFFSET] = normhipart;
    while (--n)
    {
        dphase = (double)(in++ * (float)(COSTABSIZE)) + UNITBIT32;
            frac = tf.tf_d - UNITBIT32;
        tf.tf_d = dphase;
            f1 = addr[0];
            f2 = addr[1];
        addr = tab + (tf.tf_i[HIOFFSET] & (COSTABSIZE-1));
            out++ = f1 + frac * (f2 - f1);
        tf.tf_i[HIOFFSET] = normhipart;
    }
            frac = tf.tf_d - UNITBIT32;
            f1 = addr[0];
            f2 = addr[1];
            out++ = f1 + frac * (f2 - f1);
            
            
            return (w+4);

            
            
            
            
            
            
	    
	}   
	
	
	
	public static void cos_dsp(t_cos x, t_signal[] sp)
	{
	    dsp_add(cos_perform, 3, sp[0].s_vec, sp[1].s_vec, sp[0].s_n);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void cos_maketable()
	{
	    int i;
	    float fp, phase;
	    float phsinc = (2. * 3.14159) / COSTABSIZE;
	     tabfudge tf;
	    
	    if (cos_table) return;
	    cos_table = (float)getbytes(64 * (COSTABSIZE+1));
	    for (i = COSTABSIZE + 1, fp = cos_table, phase = 0; i--; fp++, phase += phsinc)
	            fp = cos(phase);

	        /* here we check at startup whether the byte alignment
	            is as we declared it.  If not, the code has to be
	            recompiled the other way. */
	    tf.tf_d = UNITBIT32 + 0.5;
	    if ((unsigned)tf.tf_i[LOWOFFSET] != 0x80000000)
	        bug("cos~: unexpected machine alignment");
	}
	
	
	
	
	public static void cos_setup()
	{
	    cos_class = class_new(gensym("cos~"), (t_newmethod)cos_new, 0, t_cos, 0, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(cos_class, t_cos, x_f);
	    class_addmethod(cos_class, (t_method)cos_dsp, gensym("dsp"), 0);
	    cos_maketable();
	}
	
	
	
	class t_osc {
		
		t_object x_obj;
		double x_phase;
		float x_conv;
		float x_f;
		
		
	}
	
	
	
	
	public static void osc_new(t_floatarg f)
	{
	    t_osc x = (t_osc )pd_new(osc_class);
	    x.x_f = f;
	    outlet_new(x.x_obj, gensym("signal"));
	    inlet_new(x.x_obj, x.x_obj.ob_pd, s_float, gensym("ft1"));
	    x.x_phase = 0;
	    x.x_conv = 0;
	    return (x);
	}
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static t_int osc_perform(t_int[] w)
	{
		t_osc x = (t_osc)(w[1]);
	    t_float in = (t_float )(w[2]);
	    t_float out = (t_float )(w[3]);
	    int n = (int)(w[4]);
	    float tab = cos_table;
	    float [] addr;
	    float f1, f2, frac;
	    double dphase = x.x_phase + UNITBIT32;
	    int normhipart;
	    tabfudge tf;
	    float conv = x.x_conv;
	    tf.tf_d = UNITBIT32;
	    normhipart = tf.tf_i[HIOFFSET];
	    
	    
	    
	    
	    
	    
	    tf.tf_d = dphase;
	    dphase += in++ * conv;
	    
        addr = tab + (tf.tf_i[HIOFFSET] & (COSTABSIZE-1));
        tf.tf_i[HIOFFSET] = normhipart;
        frac = tf.tf_d - UNITBIT32;
        
    while (--n)
    {
        tf.tf_d = dphase;
            f1 = addr[0];
       dphase += in++ * conv;
       		f2 = addr[1];
       addr = tab + (tf.tf_i[HIOFFSET] & (COSTABSIZE-1));
       tf.tf_i[HIOFFSET] = normhipart;

            out++ = f1 + frac * (f2 - f1);
       frac = tf.tf_d - UNITBIT32;
    }
            f1 = addr[0];
            f2 = addr[1];
            out++ = f1 + frac * (f2 - f1);
            
            
            tf.tf_d = UNITBIT32 * COSTABSIZE;
            normhipart = tf.tf_i[HIOFFSET];
            tf.tf_d = dphase + (UNITBIT32 * COSTABSIZE - UNITBIT32);
            tf.tf_i[HIOFFSET] = normhipart;
            x.x_phase = tf.tf_d - UNITBIT32 * COSTABSIZE;
            return (w+5);

            
            
            
            
            
            
	    
	} 
	
	
	
	
	public static void osc_dsp(t_osc x, t_signal[] sp)
	{
	    x.x_conv = COSTABSIZE/sp[0].s_sr;
	    dsp_add(osc_perform, 4, x, sp..s_vec, sp[1].s_vec, sp[0].s_n);
	}
	
	
	public static void osc_ft1(t_osc x, t_float f)
	{
	    x.x_phase = COSTABSIZE * f;
	}

	
	
	static void osc_setup()
	{    
	    osc_class = class_new(gensym("osc~"), (t_newmethod)osc_new, 0, t_osc, 0, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(osc_class, t_osc, x_f);
	    class_addmethod(osc_class, (t_method)osc_dsp, gensym("dsp"), 0);
	    class_addmethod(osc_class, (t_method)osc_ft1, gensym("ft1"), A_FLOAT, 0);

	    cos_maketable();
	}
	
	
	
	class t_vcfctl {
		
		float c_re;
		float c_im;
		float c_q;
		float c_isr;
		
		
	}
	
	
	class t_sigvcf {
		
		t_object x_objc
		t_vcfctl x_cspace;
		t_vcfctl x_ctl;
		float x_f;
	}
	
	
	
	
	
	public static void sigvcf_new(t_floatarg q))
	{
	    t_sigvcf x = (t_sigvcf )pd_new(sigvcf_class);
	    inlet_new(x.x_obj, x.x_obj.ob_pd, s_signal, s_signal);
	    inlet_new(x.x_obj, x.x_obj.ob_pd, gensym("float"), gensym("ft1"));
	    outlet_new(x.x_obj, gensym("signal"));
	    outlet_new(x.x_obj, gensym("signal"));
	    x.x_ctl = x.x_cspace;
	    x.x_cspace.c_re = 0;
	    x.x_cspace.c_im = 0;
	    x.x_cspace.c_q = q;
	    x_cspace.c_isr = 0;
	    x.x_f = 0;
	    return (x);
	}
	
	
	
	public static void sigvcf_ft1(t_sigvcf x, t_floatarg f)
	{
	    x.x_ctl.c_q = (f > 0 ? f : 0.f);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static t_int sigvcf_perform(t_int[] w)
	{
	    float in1 = (float )(w[1]);
	    float in2 = (float )(w[2]);
	    float out1 = (float)(w[3]);
	    float out2 = (float)(w[4]);
	    t_vcfctl c = (t_vcfctl )(w[5]);
	    int n = (t_int)(w[6]);
	    int i;
	    float re = c.c_re, re2;
	    float im = c.c_im;
	    float q = c.c_q;
	    float qinv = (q > 0? 1.0f/q : 0);
	    float ampcorrect = 2.0f - 2.0f / (q + 2.0f);
	    float isr = c.c_isr;
	    float coefr, coefi;
	    float tab = cos_table;
	    float [] addr;
	    float f1, f2, frac;
	    double dphase;
	    int normhipart, tabindex;
	    tabfudge tf;
	    
	    tf.tf_d = UNITBIT32;
	    normhipart = tf.tf_i[HIOFFSET];

	    for (i = 0; i < n; i++)
	    {
	        float cf, cfindx, r, oneminusr;
	        cf = in2++ * isr;
	        if (cf < 0) cf = 0;
	        cfindx = cf * (float)(COSTABSIZE/6.28318f);
	        r = (qinv > 0 ? 1 - cf * qinv : 0);
	        if (r < 0) r = 0;
	        oneminusr = 1.0f - r;
	        dphase = ((double)(cfindx)) + UNITBIT32;
	        tf.tf_d = dphase;
	        tabindex = tf.tf_i[HIOFFSET] & (COSTABSIZE-1);
	        addr = tab + tabindex;
	        tf.tf_i[HIOFFSET] = normhipart;
	        frac = tf.tf_d - UNITBIT32;
	        f1 = addr[0];
	        f2 = addr[1];
	        coefr = r * (f1 + frac * (f2 - f1));

	        addr = tab + ((tabindex - (COSTABSIZE/4)) & (COSTABSIZE-1));
	        f1 = addr[0];
	        f2 = addr[1];
	        coefi = r * (f1 + frac * (f2 - f1));

	        f1 = in1++;
	        re2 = re;
	        out1++ = re = ampcorrect * oneminusr * f1 + coefr * re2 - coefi * im;
	        out2++ = im = coefi * re2 + coefr * im;
	    }
	    if (PD_BIGORSMALL(re))
	        re = 0;
	    
	    if (PD_BIGORSMALL(im))
	        im = 0;
	    c.c_re = re;
	    c.c_im = im;
	    return (w+7);
	}
	
	
	
	
	
	
	
	public static void sigvcf_dsp(t_sigvcf x, t_signal[] sp)
	{
	    x.x_ctl.c_isr = 6.28318f/sp[0].s_sr;
	    dsp_add(sigvcf_perform, 6, sp[0].s_vec, sp[1].s_vec, sp[2].s_vec, sp[3].s_vec, 
	            x.x_ctl, sp[0].s_n);

	}
	
	
	
	public static void sigvcf_setup()
	{
	    sigvcf_class = class_new(gensym("vcf~"), (t_newmethod)sigvcf_new, 0,t_sigvcf, 0, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(sigvcf_class, t_sigvcf, x_f);
	    class_addmethod(sigvcf_class, (t_method)sigvcf_dsp, gensym("dsp"), 0);
	    class_addmethod(sigvcf_class, (t_method)sigvcf_ft1, gensym("ft1"), A_FLOAT, 0);
	}
	
	
	
	class t_noise {
		
		t_object x_obj;
		int x_val;
	}
	
	
	
	
	public static void noise_new()
	{
	    t_noise x = (t_noise )pd_new(noise_class);
	    static int init = 307;
	    init *= 1319;
	    x.x_val = init;
	    outlet_new(x.x_obj, gensym("signal"));
	   // return (x);
	}
	
	
	
	
	
	public static t_int noise_perform(t_int[] w)
	{
	    t_sample out = (t_sample )(w[1]);
	    int vp = (int )(w[2]);
	    int n = (int)(w[3]);
	    int val = vp;
	    while (n--)
	    {
	        out++ = ((float)((val & 0x7fffffff) - 0x40000000)) * (float)(1.0 / 0x40000000);
	        val = val * 435898247 + 382842987;
	    }
	    vp = val;
	    return (w+4);
	}
	
	
	
	
	public static void noise_dsp(t_noise x, t_signal[] sp)
	{
	    dsp_add(noise_perform, 3, sp[0].s_vec, x.x_val, sp[0].s_n);
	}
	
	
	
	public static void noise_setup()
	{
	    noise_class = class_new(gensym("noise~"), (t_newmethod)noise_new, 0, t_noise, 0, 0);
	    class_addmethod(noise_class, (t_method)noise_dsp, gensym("dsp"), 0);
	}
	
	public static void d_osc_setup()
	{
	    phasor_setup();
	    cos_setup();
	    osc_setup();
	    sigvcf_setup();
	    noise_setup();
	}
	
	
	
	
	
	
	
	
	
	
	
	//d_ctl.c
	
	
	class t_sig {
		t_object x_obj;
		t_float x_f;
		
	}
	
	
	public static t_int sig_tilde_perform(t_int[] w)
	{
	    t_float f = (t_float )(w[1]);
	    t_sample out = (t_sample )(w[2]);
	    int n = (int)(w[3]);
	    while (n--)
	        out++ = f; 
	    return (w+4);
	}
	
	
	
	
	public static t_int sig_tilde_perf8(t_int[] w)
	{
	    t_float f = (t_float )(w[1]);
	    t_sample out = (t_sample )(w[2]);
	    int n = (int)(w[3]);
	    
	    for (; n; n -= 8, out += 8)
	    {
	        out[0] = f;
	        out[1] = f;
	        out[2] = f;
	        out[3] = f;
	        out[4] = f;
	        out[5] = f;
	        out[6] = f;
	        out[7] = f;
	    }
	    return (w+4);
	}
	
	
	public static void dsp_add_scalarcopy(t_float in, t_sample out, int n)
	{
	    if (n&7)
	        dsp_add(sig_tilde_perform, 3, in, out, n);
	    else        
	        dsp_add(sig_tilde_perf8, 3, in, out, n);
	}
	
	
	public static void sig_tilde_float(t_sig x, t_float f)
	{
	    x.x_f = f;
	}
	
	
	static void sig_tilde_dsp(t_sig x, t_signal[] sp)
	{
	    dsp_add(sig_tilde_perform, 3, x.x_f, sp[0].s_vec, sp[0].>s_n);
	}
	
	
	public static void sig_tilde_new(t_floatarg f)
	{
	    t_sig x = (t_sig )pd_new(sig_tilde_class);
	    x.x_f = f;
	    outlet_new(x.x_obj, gensym("signal"));
	    return (x);
	}
	
	
	static void sig_tilde_setup()
	{
	    sig_tilde_class = class_new(gensym("sig~"), (t_newmethod)sig_tilde_new, 0, t_sig, 0, A_DEFFLOAT, 0);
	    class_addfloat(sig_tilde_class, (t_method)sig_tilde_float);
	    class_addmethod(sig_tilde_class, (t_method)sig_tilde_dsp, gensym("dsp"), 0);
	}
	
	class t_line {
		
		   t_object x_obj;
		    t_sample x_target; /* target value of ramp */
		    t_sample x_value; /* current value of ramp at block-borders */
		    t_sample x_biginc;
		    t_sample x_inc;
		    t_float x_1overn;
		    t_float x_dspticktomsec;
		    t_float x_inletvalue;
		    t_float x_inletwas;
		    int x_ticksleft;
		    int x_retarget;
	}
	
	
	

	public static t_int line_tilde_perform(t_int[] w)
	{
	    t_line x = (t_line)(w[1]);
	    t_sample out = (t_sample)(w[2]);
	    int n = (int)(w[3]);
	    t_sample f = x.x_value;

	    if (PD_BIGORSMALL(f))
	            x.x_value = f = 0;
	    if (x.x_retarget)
	    {
	        int nticks = x.x_inletwas * x.x_dspticktomsec;
	        if (nticks == 0) 
	        	nticks = 1;
	        x.x_ticksleft = nticks;
	        x.x_biginc = (x.x_target - x.x_value)/(t_float)nticks;
	        x.x_inc = x.x_1overn * x.x_biginc;
	        x.x_retarget = 0;
	    }
	    if (x.x_ticksleft)
	    {
	        t_sample f = x.x_value;
	        while (n--) {
	        	sout++ = f;
	        f += x.x_inc;
	        
	        } 
	        x.x_value += x.x_biginc;
	        x.x_ticksleft--;
	    }
	    else
	    {
	        t_sample g = x.x_value = x.x_target;
	        while (n--)
	            out++ = g;
	    }
	    return (w+4);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* TB: vectorized version */
	public static t_int line_tilde_perf8(t_int[] w)
	{
	    t_line x = (t_line )(w[1]);
	    t_sample out = (t_sample )(w[2]);
	    int n = (int)(w[3]);
	    t_sample f = x.x_value;

	    if (PD_BIGORSMALL(f))
	        x.x_value = f = 0;
	    if (x.x_retarget)
	    {
	        int nticks = x.x_inletwas * x.x_dspticktomsec;
	        if (nticks == 0 ) nticks = 1;
	        x.x_ticksleft = nticks;
	        x.x_biginc = (x.x_target - x.x_value)/(t_sample)nticks;
	        x.x_inc = x.x_1overn * x.x_biginc;
	        x.x_retarget = 0;
	    }
	    if (x.x_ticksleft)
	    {
	        t_sample f = x.x_value;
	        while (n--) 
	        	out++ = f;
	        f += x.x_inc;
	        
	        x.x_value += x.x_biginc;
	        x.x_ticksleft--;
	    }
	    else
	    {
	        t_sample f = x.x_value = x.x_target;
	        for (; n; n -= 8, out += 8)
	        {
	            out[0] = f; out[1] = f; out[2] = f; out[3] = f; 
	            out[4] = f; out[5] = f; out[6] = f; out[7] = f;
	        }
	    }
	    return (w+4);
	}
	
	
	
	
	
	
	public static void line_tilde_float(t_line x, t_float f)
	{
	    if (x.x_inletvalue <= 0)
	    {
	        x.x_target = x.x_value = f;
	        x.x_ticksleft = x.x_retarget = 0;
	    }
	    else
	    {
	        x.x_target = f;
	        x.x_retarget = 1;
	        x.x_inletwas = x.x_inletvalue;
	        x.x_inletvalue = 0;
	    }
	}
	
	
	
	
	
	public static void line_tilde_stop(t_line x)
	{
	    x.x_target = x.x_value;
	    x.x_ticksleft = x.x_retarget = 0;
	}
	
	
	
	
	
	public static void line_tilde_dsp(t_line x, t_signal[] sp)
	{
	    if(sp[0].s_n&7)
	        dsp_add(line_tilde_perform, 3, x, sp[0].s_vec, sp[0]._n);
	    else
	        dsp_add(line_tilde_perf8, 3, x, sp[0].s_vec, sp[0].s_n);
	    x.x_1overn = 1./sp[0].s_n;
	    x.x_dspticktomsec = sp[0].s_sr / (1000 * sp[0].s_n);
	}
	
	
	
	public static void line_tilde_new()
	{
	    t_line x = (t_line )pd_new(line_tilde_class);
	    outlet_new(x.x_obj, gensym("signal"));
	    floatinlet_new(x.x_retarget>x_obj, x.x_inletvalue);
	    x.x_ticksleft = x.x_retarget = 0;
	    x.x_value = x.x_target = x.x_inletvalue = x.x_inletwas = 0;
	   // return (x);
	}
	
	
	public static void line_tilde_setup()
	{
	    line_tilde_class = class_new(gensym("line~"), line_tilde_new, 0,t_line, 0, 0);
	    class_addfloat(line_tilde_class, (t_method)line_tilde_float);
	    class_addmethod(line_tilde_class, (t_method)line_tilde_dsp, gensym("dsp"), 0);
	    class_addmethod(line_tilde_class, (t_method)line_tilde_stop, gensym("stop"), 0);
	}
	
	
	class t_vseg {
		 double s_targettime;
		 double s_starttime;
		 t_sample s_target;
		 t_vseg s_next;		
		
	}
	
	class t_vline {
		
		  t_object x_obj;
		    double x_value;
		    double x_inc;
		    double x_referencetime;
		    double x_samppermsec;
		    double x_msecpersamp;
		    double x_targettime;
		    t_sample x_target;
		    t_float x_inlet1;
		    t_float x_inlet2;
		    t_vseg[] x_list;
		    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static t_int vline_tilde_perform(t_int[] w)
	{
	    t_vline x = (t_vline )(w[1]);
	    t_float out = (t_float )(w[2]);
	    int n = (int)(w[3]), i;
	    double f = x.x_value;
	    double inc = x.x_inc;
	    double msecpersamp = x.x_msecpersamp;
	    double samppermsec = x.x_samppermsec;
	    double timenow = clock_gettimesince(x.x_referencetime) - n * msecpersamp;
	    t_vseg *s = x.x_list;
	    for (i = 0; i < n; i++)
	    {
	        double timenext = timenow + msecpersamp;
	    checknext:
	        if (s)
	        {
	            /* has starttime elapsed?  If so update value and increment */
	            if (s.s_starttime < timenext)
	            {
	                if (x.x_targettime <= timenext)
	                    f = x.x_target, inc = 0;
	                    /* if zero-length segment bash output value */
	                if (s.s_targettime <= s.s_starttime)
	                {
	                    f = s.s_target;
	                    inc = 0;
	                }
	                else
	                {
	                    double incpermsec = (s.s_target - f)/
	                        (s.s_targettime - s.s_starttime);
	                    f = f + incpermsec * (timenext - s.s_starttime);
	                    inc = incpermsec * msecpersamp;
	                }
	                x.x_inc = inc;
	                x.x_target = s.s_target;
	                x.x_targettime = s.s_targettime;
	                x.x_list = s.s_next;
	                t_freebytes(s, s);
	                s = x.x_list;
	                goto checknext;
	            }
	        }
	        if (x.x_targettime <= timenext)
	            f = x.x_target, inc = x.x_inc = 0, x.x_targettime = 1e20;
	        out++ = f;
	        f = f + inc;
	        timenow = timenext;
	    }
	    x.x_value = f;
	    return (w+4);
	}

	
	
	
	public static void vline_tilde_stop(t_vline x)
	{
	    t_vseg s1, s2;
	    for (s1 = x.x_list; s1; s1 = s2)
	        s2 = s1.s_next, t_freebytes(s1, s1);
	    x.x_list = 0;
	    x.x_inc = 0;
	    x.x_inlet1 = x.x_inlet2 = 0;
	    x.x_target = x.x_value;
	    x.x_targettime = 1e20;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void vline_tilde_float(t_vline x, t_float f)
	{
	    double timenow = clock_gettimesince(x.x_referencetime);
	    t_float inlet1 = (x.x_inlet1 < 0 ? 0 : x.x_inlet1);
	    t_float inlet2 = x.x_inlet2;
	    double starttime = timenow + inlet2;
	    t_vseg s1, s2, deletefrom = 0, snew;
	    if (PD_BIGORSMALL(f))
	        f = 0;

	        /* negative delay input means stop and jump immediately to new value */
	    if (inlet2 < 0)
	    {
	        x.x_value = f;
	        vline_tilde_stop(x);
	        return;
	    }
	    snew = (t_vseg )t_getbytes(snew);
	        /* check if we supplant the first item in the list.  We supplant
	        an item by having an earlier starttime, or an equal starttime unless
	        the equal one was instantaneous and the new one isn't (in which case
	        we'll do a jump-and-slide starting at that time.) */
	    if (!x.x_list || x.x_list.s_starttime > starttime ||
	        (x.x_list.s_starttime == starttime &&
	            (x.x_list.s_targettime > x.x_list.s_starttime || inlet1 <= 0)))
	    {
	        deletefrom = x.x_list;
	        x.x_list = snew;
	    }
	    else
	    {
	        for (s1 = x.x_list; s2 = s1.s_next; s1 = s2)
	        {
	            if (s2.s_starttime > starttime ||
	                (s2.s_starttime == starttime &&
	                    (s2.s_targettime > s2.s_starttime || inlet1 <= 0)))
	            {
	                deletefrom = s2;
	                s1.s_next = snew;
	                goto didit; //go to won't work right now
	            }
	        }
	        s1.s_next = snew;
	        deletefrom = 0;
	    didit: ;
	    }
	    while (deletefrom)
	    {
	        s1 = deletefrom.s_next;
	        t_freebytes(deletefrom, deletefrom);
	        deletefrom = s1;
	    }
	    snew.s_next = 0;
	    snew.s_target = f;
	    snew.s_starttime = starttime;
	    snew.s_targettime = starttime + inlet1;
	    x.x_inlet1 = x.x_inlet2 = 0;
	}
	
	
	
	
	public static void vline_tilde_dsp(t_vline x, t_signal[] sp)
	{
	    dsp_add(vline_tilde_perform, 3, x, sp[0].s_vec, sp[0].s_n);
	    x.x_samppermsec = ((double)(sp[0].s_sr)) / 1000;
	    x.x_msecpersamp = ((double)1000) / sp[0].s_sr;
	}
	
	
	
	
	
	
	public static void vline_tilde_new()
	{
	    t_vline x = (t_vline )pd_new(vline_tilde_class);
	    outlet_new(x.x_obj, gensym("signal"));
	    floatinlet_new(x.x_obj, x.x_inlet1);
	    floatinlet_new(x.x_obj, x.x_inlet2);
	    x.x_inlet1 = x.x_inlet2 = 0;
	    x.x_value = x.x_inc = 0;
	    x.x_referencetime = clock_getlogicaltime();
	    x.x_list = 0;
	    x.x_samppermsec = 0;
	    x.x_targettime = 1e20;
	    return (x);
	}
	
	
	public static void vline_tilde_setup()
	{
	    vline_tilde_class = class_new(gensym("vline~"), vline_tilde_new, (t_method)vline_tilde_stop, t_vline, 0, 0);
	    class_addfloat(vline_tilde_class, (t_method)vline_tilde_float);
	    class_addmethod(vline_tilde_class, (t_method)vline_tilde_dsp,
	        gensym("dsp"), 0);
	    class_addmethod(vline_tilde_class, (t_method)vline_tilde_stop,
	        gensym("stop"), 0);
	}
	
	
	class t_snapshot {
		
		
		t_object x_obj;
		t_sample x_value;
		t_flot x_f;
	}
	
	public static void *snapshot_tilde_new(void)
	{
	    t_snapshot x = (t_snapshot )pd_new(snapshot_tilde_class);
	    x.x_value = 0;
	    outlet_new(x.x_obj, s_float);
	    x.x_f = 0;
	    return (x);
	}
	
	
	public static t_int snapshot_tilde_perform(t_int[] w)
	{
	    t_sample in = (t_sample )(w[1]);
	    t_sample out = (t_sample )(w[2]);
	    out = in;
	    return (w[3]);
	}
	
	
	public static void snapshot_tilde_dsp(t_snapshot x, t_signal[] sp)
	{
	    dsp_add(snapshot_tilde_perform, 2, sp[0].s_vec + (sp[0].s_n-1),
	        x.x_value);
	}
	
	public static void snapshot_tilde_bang(t_snapshot x)
	{
	    outlet_float(x.x_obj.ob_outlet, x.x_value);
	}
	
	
	public static void snapshot_tilde_set(t_snapshot x, t_floatarg f)
	{
	    x.x_value = f;
	}
	
	public static void snapshot_tilde_setup()
	{
	    snapshot_tilde_class = class_new(gensym("snapshot~"), snapshot_tilde_new, 0, t_snapshot, 0, 0);
	    CLASS_MAINSIGNALIN(snapshot_tilde_class, t_snapshot, x_f);
	    class_addmethod(snapshot_tilde_class, (t_method)snapshot_tilde_dsp, gensym("dsp"), 0);
	    class_addmethod(snapshot_tilde_class, (t_method)snapshot_tilde_set, gensym("set"), A_DEFFLOAT, 0);
	    class_addbang(snapshot_tilde_class, snapshot_tilde_bang);
	}
	
	class t_vsnapshot {
		t_object x_obj;
	    int x_n;
	    int x_gotone;
	    t_sample x_vec;
	    t_float x_f;
	    t_float x_sampspermsec;
	    double x_time;
	    t_object x_obj;
	    int x_n;
	    int x_gotone;
	    t_sample x_vec;
	    t_float x_f;
	    t_float x_sampspermsec;
	    double x_time;		
		
	}
	
	public static void vsnapshot_tilde_new()
	{
	    t_vsnapshot x = (t_vsnapshot)pd_new(vsnapshot_tilde_class);
	    outlet_new(x.x_obj, s_float);
	    x.x_f = 0;
	    x.x_n = 0;
	    x.x_vec = 0;
	    x.x_gotone = 0;
	    return (x);
	}
	
	static t_int vsnapshot_tilde_perform(t_int[] w)
	{
	    t_sample in = (t_sample )(w[1]);
	    t_vsnapshot x = (t_vsnapshot )(w[2]);
	    t_sample out = x.x_vec;
	    int n = x.x_n, i;
	    for (i = 0; i < n; i++)
	        out[i] = in[i];
	    x.x_time = clock_getlogicaltime();
	    x.x_gotone = 1;
	    return (w[3]);
	}
	
	
	public static void vsnapshot_tilde_dsp(t_vsnapshot x, t_signal[] sp)
	{
	    int n = sp[0].s_n;
	    if (n != x.x_n)
	    {
	        if (x.x_vec)
	            t_freebytes(x.x_vec, x.x_n * t_sample);
	        x.x_vec = (t_sample)getbytes(n * t_sample);
	        x.x_gotone = 0;
	        x.x_n = n;
	    }
	    x.x_sampspermsec = sp[0].s_sr / 1000;
	    dsp_add(vsnapshot_tilde_perform, 2, sp[0].s_vec, x);
	}
	
	
	public static void vsnapshot_tilde_bang(t_vsnapshot x)
	{
	    t_sample val;
	    if (x.x_gotone)
	    {
	        int indx = clock_gettimesince(x.x_time) * x.x_sampspermsec;
	        if (indx < 0)
	            indx = 0;
	        else if (indx >= x.x_n)
	            indx = x.x_n - 1;
	        val = x.x_vec[indx];
	    }
	    else val = 0;
	    outlet_float(x.x_obj.ob_outlet, val);
	}
	
	static void vsnapshot_tilde_ff(t_vsnapshot x)
	{
	    if (x.x_vec)
	        t_freebytes(x.x_vec, x.x_n * t_sample);
	}
	
	public static void vsnapshot_tilde_setup()
	{
	    vsnapshot_tilde_class = class_new(gensym("vsnapshot~"), vsnapshot_tilde_new, (t_method)vsnapshot_tilde_ff, t_vsnapshot, 0, 0);
	    CLASS_MAINSIGNALIN(vsnapshot_tilde_class, t_vsnapshot, x_f);
	    class_addmethod(vsnapshot_tilde_class, (t_method)vsnapshot_tilde_dsp, gensym("dsp"), 0);
	    class_addbang(vsnapshot_tilde_class, vsnapshot_tilde_bang);
	}
	

	class t_sigenv {
		  t_object x_obj;                 /* header */
		    void x_outlet;                 /* a "float" outlet */
		    void x_clock;                  /* a "clock" object */
		    t_sample x_buf;                   /* a Hanning window */
		    int x_phase;                    /* number of points since last output */
		    int x_period;                   /* requested period of output */
		    int x_realperiod;               /* period rounded up to vecsize multiple */
		    int x_npoints;                  /* analysis window size in samples */
		    t_float x_result;                 /* result to output */
		    t_sample[] x_sumbuf = new t_sample[32];     /* summing buffer */
		    t_float x_f;
		    int x_allocforvs;               /* extra buffer for DSP vector size */
		
		
		
	}
	
	
	
	public static void env_tilde_new(t_floatarg fnpoints, t_floatarg fperiod)
	{
	    int npoints = fnpoints;
	    int period = fperiod;
	    t_sigenv x;
	    t_sample buf;
	    int i;

	    if (npoints < 1) npoints = 1024;
	    if (period < 1) period = npoints/2;
	    if (period < npoints / 32 + 1)
	        period = npoints / 32 + 1;
	    if (!(buf = getbytes(t_sample * (npoints + 64))))
	    {
	        error("env: couldn't allocate buffer");
	        return (0);
	    }
	    x = (t_sigenv )pd_new(env_tilde_class);
	    x.x_buf = buf;
	    x.x_npoints = npoints;
	    x.x_phase = 0;
	    x.x_period = period;
	    for (i = 0; i < 32; i++) x.x_sumbuf[i] = 0;
	    for (i = 0; i < npoints; i++)
	        buf[i] = (1. - cos((2 * 3.14159 * i) / npoints))/npoints;
	    for (; i < npoints+64; i++) buf[i] = 0;
	    x.x_clock = clock_new(x, (t_method)env_tilde_tick);
	    x.x_outlet = outlet_new(x.x_obj, gensym("float"));
	    x.x_f = 0;
	    x.x_allocforvs = 64;
	    return (x);
	}
	
	
	
	
	
	
	
	
	
	
	
	public static t_int env_tilde_perform(t_int[] w)
	{
	    t_sigenv x = (t_sigenv )(w[1]);
	    t_sample in = (t_sample )(w[2]);
	    int n = (int)(w[3]);
	    int count;
	    t_sample sump; 
	    in += n;
	    for (count = x.x_phase, sump = x.x_sumbuf;
	        count < x.x_npoints; count += x.x_realperiod, sump++)
	    {
	        t_sample hp = x.x_buf + count;
	        t_sample fp = in;
	        t_sample sum = sump;
	        int i;
	        
	        for (i = 0; i < n; i++)
	        {
	            fp--;
	            sum += hp++ * (fp * fp);
	        }
	        sump = sum;
	    }
	    sump[0] = 0;
	    x.x_phase -= n;
	    if (x.x_phase < 0)
	    {
	        x.x_result = x.x_sumbuf[0];
	        for (count = x.x_realperiod, sump = x.x_sumbuf;
	            count < x.x_npoints; count += x.x_realperiod, sump++)
	                sump[0] = sump[1];
	        sump[0] = 0;
	        x.x_phase = x.x_realperiod - n;
	        clock_delay(x.x_clock, 0L);
	    }
	    return (w[4]);
	}
	
	
	
	public static void env_tilde_dsp(t_sigenv x, t_signal[] sp)
	{
	    if (x.x_period % sp[0].s_n) x.x_realperiod = x.x_period + sp[0].s_n - (x.x_period % sp[0].s_n);
	    else x.x_realperiod = x.x_period;
	    if (sp[0].s_n > x.x_allocforvs)
	    {
	        Object xx = resizebytes(x.x_buf, (x.x_npoints + x.x_allocforvs) * t_sample, (x.x_npoints + sp[0].s_n) * t_sample);
	        if (xx == null)
	        {
	            error("env~: out of memory");
	            return;
	        }
	        x.x_buf = (t_sample )xx;
	        x.x_allocforvs = sp[0].s_n;
	    }
	    dsp_add(env_tilde_perform, 3, x, sp[0].s_vec, sp[0].s_n);
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void env_tilde_tick(t_sigenv x) /* callback function for the clock */
	{
	    outlet_float(x.x_outlet, powtodb(x.x_result));
	}

	public static void env_tilde_ff(t_sigenv x)           /* cleanup on free */
	{
	    clock_free(x.x_clock);
	    freebytes(x.x_buf, (x.x_npoints + x.x_allocforvs) * x.x_buf);
	}


	public void env_tilde_setup()
	{
	    env_tilde_class = class_new(gensym("env~"), (t_newmethod)env_tilde_new, (t_method)env_tilde_ff, t_sigenv, 0, A_DEFFLOAT, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(env_tilde_class, t_sigenv, x_f);
	    class_addmethod(env_tilde_class, (t_method)env_tilde_dsp, gensym("dsp"), 0);
	}
	
	class t_threshold_tilde {
		
	    t_object x_obj;
	    t_outlet x_outlet1;        /* bang out for high thresh */
	    t_outlet x_outlet2;        /* bang out for low thresh */
	    t_clock x_clock;           /* wakeup for message output */
	    t_float x_f;                  /* scalar inlet */
	    int x_state;                /* 1 = high, 0 = low */
	    t_float x_hithresh;           /* value of high threshold */
	    t_float x_lothresh;           /* value of low threshold */
	    t_float x_deadwait;           /* msec remaining in dead period */
	    t_float x_msecpertick;        /* msec per DSP tick */
	    t_float x_hideadtime;         /* hi dead time in msec */
	    t_float x_lodeadtime;         /* lo dead time in msec */
		
		
	}
	
	
	public static t_threshold_tilde threshold_tilde_new(t_floatarg hithresh, t_floatarg hideadtime, t_floatarg lothresh, t_floatarg lodeadtime)
	{
	    t_threshold_tilde x = (t_threshold_tilde ) pd_new(threshold_tilde_class);
	    x.x_state = 0;             /* low state */
	    x.x_deadwait = 0;          /* no dead time */
	    x.x_clock = clock_new(x, (t_method)threshold_tilde_tick);
	    x.x_outlet1 = outlet_new(x.x_obj, s_bang);
	    x.x_outlet2 = outlet_new(x.x_obj, s_bang);
	    inlet_new(x.x_obj, x.x_obj.ob_pd, s_float, gensym("ft1"));
	    x.x_msecpertick = 0.;
	    x.x_f = 0;
	    threshold_tilde_set(x, hithresh, hideadtime, lothresh, lodeadtime);
	    return (x);
	}
	
	   /* "set" message to specify thresholds and dead times */
	public static void threshold_tilde_set(t_threshold_tilde x, t_floatarg hithresh, t_floatarg hideadtime,
	    t_floatarg lothresh, t_floatarg lodeadtime)
	{
	    if (lothresh > hithresh)
	        lothresh = hithresh;
	    x.x_hithresh = hithresh;
	    x.x_hideadtime = hideadtime;
	    x.x_lothresh = lothresh;
	    x.x_lodeadtime = lodeadtime;
	}
	
	
    /* number in inlet sets state -- note incompatible with JMAX which used
    "int" message for this, impossible here because of auto signal conversion */
	public static void threshold_tilde_ft1(t_threshold_tilde x, t_floatarg f)
{
    x.x_state = (f != 0);
    x.x_deadwait = 0;
}
	
	
	public static void threshold_tilde_tick(t_threshold_tilde x)  
{
    if (x.x_state)
        outlet_bang(x.x_outlet1);
    else outlet_bang(x.x_outlet2);
}

	
	
	
	
	
	
	
	
	public static t_int threshold_tilde_perform(t_int[] w)
	{
	    t_sample in1 = (t_sample )(w[1]);
	    t_threshold_tilde x = (t_threshold_tilde )(w[2]);
	    int n = (t_int)(w[3]);
	    if (x.x_deadwait > 0)
	        x.x_deadwait -= x.x_msecpertick;
	    else if (x.x_state)
	    {
	            /* we're high; look for low sample */
	        for (; n--; in1++)
	        {
	            if (in1 < x.x_lothresh)
	            {
	                clock_delay(x.x_clock, 0L);
	                x.x_state = 0;
	                x.x_deadwait = x.x_lodeadtime;
	                break;
	            }
	        }
	    }
	    else
	    {
	            /* we're low; look for high sample */
	        for (; n--; in1++)
	        {
	            if (in1 >= x.x_hithresh)
	            {
	                clock_delay(x.x_clock, 0L);
	                x.x_state = 1;
	                x.x_deadwait = x.x_hideadtime;
	                break;
	            }
	        }
	    }

	    return (w+4);
	}
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	public void threshold_tilde_dsp(t_threshold_tilde x, t_signal[] sp)
	{
	    x.x_msecpertick = 1000. * sp[0].s_n / sp[0].s_sr;
	    dsp_add(threshold_tilde_perform, 3, sp[0].s_vec, x, sp[0].s_n);
	}

	static void threshold_tilde_ff(t_threshold_tilde x)
	{
	    clock_free(x.x_clock);
	}

	static void threshold_tilde_setup()
	{
	    threshold_tilde_class = class_new(gensym("threshold~"),
	        (t_newmethod)threshold_tilde_new, (t_method)threshold_tilde_ff, t_threshold_tilde, 0,
	            A_DEFFLOAT, A_DEFFLOAT, A_DEFFLOAT, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(threshold_tilde_class, t_threshold_tilde, x_f);
	    class_addmethod(threshold_tilde_class, (t_method)threshold_tilde_set,
	        gensym("set"), A_FLOAT, A_FLOAT, A_FLOAT, A_FLOAT, 0);
	    class_addmethod(threshold_tilde_class, (t_method)threshold_tilde_ft1,
	        gensym("ft1"), A_FLOAT, 0);
	    class_addmethod(threshold_tilde_class, (t_method)threshold_tilde_dsp,
	        gensym("dsp"), 0);
	}
	
	
	
	public void d_ctl_setup()
	{
	    sig_tilde_setup();
	    line_tilde_setup();
	    vline_tilde_setup();
	    snapshot_tilde_setup();
	    vsnapshot_tilde_setup();
	    env_tilde_setup();
	    threshold_tilde_setup();
	}

	
	
	
	
	
	//d_delay.c
	
	class t_delwritectl {
		
		 int c_n;
		 t_sample c_vec;
		 int c_phase;
		
	}
	
	class t_sigdelwrite {
		
		t_object x_obj;
	    t_symbol x_sym;
	    t_float x_deltime;  /* delay in msec (added by Mathieu Bouchard) */
	    t_delwritectl x_cspace;
	    int x_sortno;   /* DSP sort number at which this was last put on chain */
	    int x_rsortno;  /* DSP sort # for first delread or write in chain */
	    int x_vecsize;  /* vector size for delread~ to use */
	    t_float x_f;
		
	}
	
	public static void sigdelwrite_updatesr (t_sigdelwrite x, t_float sr) /* added by Mathieu Bouchard */
	{
	    int nsamps = x.x_deltime * sr * (t_float)(0.001f);
	    if (nsamps < 1) nsamps = 1;
	    nsamps += ((- nsamps) & (SAMPBLK - 1));
	    nsamps += DEFDELVS;
	    if (x.x_cspace.c_n != nsamps) {
	      x.x_cspace.c_vec = (t_sample *)resizebytes(x.x_cspace.c_vec,
	        (x.x_cspace.c_n + XTRASAMPS) * t_sample,
	        (         nsamps + XTRASAMPS) * t_sample);
	      x.x_cspace.c_n = nsamps;
	      x.x_cspace.c_phase = XTRASAMPS;
	    }
	}
	
	public static void sigdelwrite_checkvecsize(t_sigdelwrite x, int vecsize)
	{
	    if (x.x_rsortno != ugen_getsortno())
	    {
	        x.x_vecsize = vecsize;
	        x.x_rsortno = ugen_getsortno();
	    }
	    /*
	        LATER this should really check sample rate and blocking, once that is
	        supported.  Probably we don't actually care about vecsize.
	        For now just suppress this check. */
	if (0) {
	    else if (vecsize != x.x_vecsize)
	        pd_error(x, "delread/delwrite/vd vector size mismatch");
	}
	}
	
	public static void sigdelwrite_new(t_symbol s, t_floatarg msec)
	{
	    t_sigdelwrite x = (t_sigdelwrite )pd_new(sigdelwrite_class);
	    if (!s.s_name) s = gensym("delwrite~");
	    pd_bind(x.x_obj.ob_pd, s);
	    x.x_sym = s;
	    x.x_deltime = msec;
	    x.x_cspace.c_n = 0;
	    x.x_cspace.c_vec = getbytes(XTRASAMPS * t_sample);
	    x.x_sortno = 0;
	    x.x_vecsize = 0;
	    x.x_f = 0;
	    return (x);
	}
	
	
	
	
	
	
	
	public static t_int sigdelwrite_perform(t_int[] w)
	{
	    t_sample in = (t_sample )(w[1]);
	    t_delwritectl c = (t_delwritectl )(w[2]);
	    int n = (int)(w[3]);
	    int phase = c.c_phase, nsamps = c.c_n;
	    t_sample vp = c.c_vec, bp = vp + phase, ep = vp + (c.c_n + XTRASAMPS);
	    phase += n;

	    while (n--)
	    {
	        t_sample f = in++;
	        if (PD_BIGORSMALL(f))
	            f = 0;
	        bp++ = f;
	        if (bp == ep)
	        {
	            vp[0] = ep[-4];
	            vp[1] = ep[-3];
	            vp[2] = ep[-2];
	            vp[3] = ep[-1];
	            bp = vp + XTRASAMPS;
	            phase -= nsamps;
	        }
	    }
	    c.c_phase = phase; 
	    return (w[4]);
	}
	
	
	
	
	public static void sigdelwrite_dsp(t_sigdelwrite x, t_signal[] sp)
	{
	    dsp_add(sigdelwrite_perform, 3, sp[0].s_vec, x.x_cspace, sp[0].s_n);
	    x.x_sortno = ugen_getsortno();
	    sigdelwrite_checkvecsize(x, sp[0].s_n);
	    sigdelwrite_updatesr(x, sp[0].s_sr);
	}

	public static void sigdelwrite_free(t_sigdelwrite x)
	{
	    pd_unbind(x.x_obj.ob_pd, x.x_sym);
	    freebytes(x.x_cspace.c_vec,
	        (x.x_cspace.c_n + XTRASAMPS) * t_sample);
	}

	public static void sigdelwrite_setup()
	{
	    sigdelwrite_class = class_new(gensym("delwrite~"), (t_newmethod)sigdelwrite_new, (t_method)sigdelwrite_free, t_sigdelwrite, 0, A_DEFSYM, A_DEFFLOAT, 0);
	    CLASS_MAINSIGNALIN(sigdelwrite_class, t_sigdelwrite, x_f);
	    class_addmethod(sigdelwrite_class, (t_method)sigdelwrite_dsp,
	        gensym("dsp"), 0);
	}
	
	class t_sigdelread {
		   t_object x_obj;
		    t_symbol x_sym;
		    t_float x_deltime;  /* delay in msec */
		    int x_delsamps;     /* delay in samples */
		    t_float x_sr;       /* samples per msec */
		    t_float x_n;        /* vector size */
		    int x_zerodel;      /* 0 or vecsize depending on read/write order */
		
	}
	
	public static void sigdelread_new(t_symbol s, t_floatarg f)
	{
	    t_sigdelread x = (t_sigdelread )pd_new(sigdelread_class);
	    x.x_sym = s;
	    x.x_sr = 1;
	    x.x_n = 1;
	    x.x_zerodel = 0;
	    sigdelread_float(x, f);
	    outlet_new(x.x_obj, s_signal);
	    return (x);
	}
	
	
	public static void sigdelread_float(t_sigdelread x, t_float f)
	{
	    int samps;
	    t_sigdelwrite delwriter =  (t_sigdelwrite )pd_findbyclass(x.x_sym, sigdelwrite_class);
	    x.x_deltime = f;
	    if (delwriter)
	    {
	        int delsize = delwriter.x_cspace.c_n;
	        x.x_delsamps = (int)(0.5 + x.x_sr * x.x_deltime) + x.x_n - x.x_zerodel;
	        if (x.x_delsamps < x.x_n) 
	        	x.x_delsamps = x.x_n;
	        else if (x.x_delsamps > delwriter.x_cspace.c_n - DEFDELVS)
	            x.x_delsamps = delwriter.x_cspace.c_n - DEFDELVS;
	    }
	}
	
	
	
	public static t_int sigdelread_perform(t_int[] w)
	{
	    t_sample out = (t_sample )(w[1]);
	    t_delwritectl c = (t_delwritectl )(w[2]);
	    int delsamps = (int )(w[3]);
	    int n = (int)(w[4]);
	    int phase = c.c_phase - delsamps, nsamps = c.c_n;
	    t_sample vp = c.c_vec, bp, ep = vp + (c.c_n + XTRASAMPS);
	    if (phase < 0) phase += nsamps;
	    bp = vp + phase;

	    while (n--)
	    {
	        out++ = bp++;
	        if (bp == ep) bp -= nsamps;
	    }
	    return (w+5);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void sigdelread_dsp(t_sigdelread x, t_signal[] sp)
	{
	    t_sigdelwrite delwriter = (t_sigdelwrite )pd_findbyclass(x.x_sym, sigdelwrite_class);
	    x.x_sr = sp[0].s_sr * 0.001;
	    x.x_n = sp[0].s_n;
	    if (delwriter)
	    {
	        sigdelwrite_updatesr(delwriter, sp[0].s_sr);
	        sigdelwrite_checkvecsize(delwriter, sp[0].s_n);
	        x.x_zerodel = (delwriter.x_sortno == ugen_getsortno() ? 0 : delwriter.x_vecsize);
	        sigdelread_float(x, x.x_deltime);
	        dsp_add(sigdelread_perform, 4, sp[0].s_vec, delwriter.x_cspace, x.x_delsamps, sp[0].s_n);
	    }
	    else if (x.x_sym.s_name)
	        error("delread~: %s: no such delwrite~",x.x_sym.s_name);
	}

	static void sigdelread_setup()
	{
	    sigdelread_class = class_new(gensym("delread~"), (t_newmethod)sigdelread_new, 0, t_sigdelread, 0, A_DEFSYM, A_DEFFLOAT, 0);
	    class_addmethod(sigdelread_class, (t_method)sigdelread_dsp, gensym("dsp"), 0);
	    class_addfloat(sigdelread_class, (t_method)sigdelread_float);
	}
	
	class t_sigvd {
		t_object x_obj;
	    t_symbol x_sym;
	    t_float x_sr;       /* samples per msec */
	    int x_zerodel;      /* 0 or vecsize depending on read/write order */
	    t_float x_f;
		
	}
	
	public static void sigvd_new(t_symbol s)
	{
	    t_sigvd x = (t_sigvd )pd_new(sigvd_class);
	    if (!s.s_name) s = gensym("vd~");
	    x.x_sym = s;
	    x.x_sr = 1;
	    x.x_zerodel = 0;
	    outlet_new(x.x_obj, s_signal);
	    x.x_f = 0;
	    return (x);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static t_int sigvd_perform(t_int[] w)
	{
	    t_sample in = (t_sample )(w[1]);
	    t_sample out = (t_sample )(w[2]);
	    t_delwritectl ctl = (t_delwritectl )(w[3]);
	    t_sigvd x = (t_sigvd )(w[4]);
	    int n = (int)(w[5]);

	    int nsamps = ctl.c_n;
	    t_sample limit = nsamps - n - 1;
	    t_sample fn = n-1;
	    t_sample vp = ctl.c_vec, bp, wp = vp + ctl.c_phase;
	    t_sample zerodel = x.x_zerodel;
	    while (n--)
	    {
	        t_sample delsamps = x.x_sr * in++ - zerodel, frac;
	        int idelsamps;
	        t_sample a, b, c, d, cminusb;
	        if (delsamps < 1.00001f) delsamps = 1.00001f;
	        if (delsamps > limit) delsamps = limit;
	        delsamps += fn;
	        fn = fn - 1.0f;
	        idelsamps = delsamps;
	        frac = delsamps - (t_sample)idelsamps;
	        bp = wp - idelsamps;
	        if (bp < vp + 4) bp += nsamps;
	        d = bp[-3];
	        c = bp[-2];
	        b = bp[-1];
	        a = bp[0];
	        cminusb = c-b;
	        *out++ = b + frac * (
	            cminusb - 0.1666667f * (1.-frac) * (
	                (d - a - 3.0f * cminusb) * frac + (d + 2.0f*a - 3.0f*b)
	            )
	        );
	    }
	    return (w[6]);
	}
	
	
	
	
	public static void sigvd_dsp(t_sigvd x, t_signal[] sp)
	{
	    t_sigdelwrite delwriter =  (t_sigdelwrite )pd_findbyclass(x.x_sym, sigdelwrite_class);
	    x.x_sr = sp[0].s_sr * 0.001;
	    if (delwriter)
	    {
	        sigdelwrite_checkvecsize(delwriter, sp[0].s_n);
	        x.x_zerodel = (delwriter.x_sortno == ugen_getsortno() ?
	            0 : delwriter.x_vecsize);
	        dsp_add(sigvd_perform, 5,
	            sp[0].s_vec, sp[1].s_vec,
	                &delwriter.x_cspace, x, sp[0].s_n);
	    }
	    else error("vd~: %s: no such delwrite~",x.x_sym.s_name);
	}
	
	
	
	
	public static void sigvd_setup()
	{
	    sigvd_class = class_new(gensym("vd~"), (t_newmethod)sigvd_new, 0, t_sigvd, 0, A_DEFSYM, 0);
	    class_addmethod(sigvd_class, (t_method)sigvd_dsp, gensym("dsp"), 0);
	    CLASS_MAINSIGNALIN(sigvd_class, t_sigvd, x_f);
	}

	/* ----------------------- global setup routine ---------------- */

	public void d_delay_setup()
	{
	    sigdelwrite_setup();
	    sigdelread_setup();
	    sigvd_setup();
	}

	
	
}