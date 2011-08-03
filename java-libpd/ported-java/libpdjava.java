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

}



























