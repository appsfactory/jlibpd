CC = mips-cibyl-elf-gcc
CFLAGS = -Os -D__CIBYL__ -DPD -DHAVE_UNISTD_H -DHAVE_LIBDL -DUSEAPI_DUMMY -D__FreeBSD__ -D__FreeBSD_kernel__ -D_POSIX_THREADS -I/home/andrew/cibyl-source/cibyl/toolchain/toolchain_build/.build/src/newlib-1.17.0/newlib/libc/include -Ilibpd/pure-data/src -Ilibpd/lipbd_wrapper -Wno-int-to-pointer-cast -Wno-pointer-to-int-cast -fPIC
PD_FILES = \
  libpd/pure-data/src/d_arithmetic.c libpd/pure-data/src/d_array.c libpd/pure-data/src/d_ctl.c \
  libpd/pure-data/src/d_dac.c libpd/pure-data/src/d_delay.c libpd/pure-data/src/d_fft.c \
  libpd/pure-data/src/d_fft_mayer.c libpd/pure-data/src/d_fftroutine.c libpd/pure-data/src/d_filter.c \
  libpd/pure-data/src/d_global.c libpd/pure-data/src/d_math.c libpd/pure-data/src/d_misc.c \
  libpd/pure-data/src/d_osc.c libpd/pure-data/src/d_resample.c libpd/pure-data/src/d_soundfile.c \
  libpd/pure-data/src/d_ugen.c libpd/pure-data/src/g_all_guis.c libpd/pure-data/src/g_array.c \
  libpd/pure-data/src/g_bang.c libpd/pure-data/src/g_canvas.c libpd/pure-data/src/g_editor.c \
  libpd/pure-data/src/g_graph.c libpd/pure-data/src/g_guiconnect.c libpd/pure-data/src/g_hdial.c \
  libpd/pure-data/src/g_hslider.c libpd/pure-data/src/g_io.c libpd/pure-data/src/g_mycanvas.c \
  libpd/pure-data/src/g_numbox.c libpd/pure-data/src/g_readwrite.c libpd/pure-data/src/g_rtext.c \
  libpd/pure-data/src/g_scalar.c libpd/pure-data/src/g_template.c libpd/pure-data/src/g_text.c \
  libpd/pure-data/src/g_toggle.c libpd/pure-data/src/g_traversal.c libpd/pure-data/src/g_vdial.c \
  libpd/pure-data/src/g_vslider.c libpd/pure-data/src/g_vumeter.c libpd/pure-data/src/m_atom.c \
  libpd/pure-data/src/m_binbuf.c libpd/pure-data/src/m_class.c libpd/pure-data/src/m_conf.c \
  libpd/pure-data/src/m_glob.c libpd/pure-data/src/m_memory.c libpd/pure-data/src/m_obj.c \
  libpd/pure-data/src/m_pd.c libpd/pure-data/src/m_sched.c libpd/pure-data/src/s_audio.c \
  libpd/pure-data/src/s_audio_dummy.c libpd/pure-data/src/s_file.c libpd/pure-data/src/s_inter.c \
  libpd/pure-data/src/s_main.c libpd/pure-data/src/s_path.c \
  libpd/pure-data/src/s_print.c libpd/pure-data/src/s_utf8.c libpd/pure-data/src/x_acoustics.c \
  libpd/pure-data/src/x_arithmetic.c libpd/pure-data/src/x_connective.c libpd/pure-data/src/x_gui.c \
  libpd/pure-data/src/x_interface.c libpd/pure-data/src/x_list.c libpd/pure-data/src/x_midi.c \
  libpd/pure-data/src/x_misc.c libpd/pure-data/src/x_qlist.c \
  libpd/pure-data/src/x_time.c libpd/libpd_wrapper/s_libpdmidi.c \
  libpd/libpd_wrapper/x_libpdreceive.c libpd/libpd_wrapper/z_libpd.c
# libpd/pure-data/src/s_loader.c
# libpd/pure-data/src/x_net.c 

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

all: java

libpd.mips: ${PD_FILES:.c=.o}
	$(CC) $(CFLAGS) -o libpd.mips ${PD_FILES:.c=.o}

java: libpd.mips
	cibyl-mips2java libpd.mips -d tmpclasses

clean: clean_o clean_mips clean_java

clean_o:
	rm libpd/pure-data/src/*.o
	rm libpd/libpd_wrapper/*.o

clean_mips:
	rm libpd.mips

clean_java:
	rm tmpclasses/*.class

# Thanks to Hans-Christoph Steiner and Mathieu Bouchard from the pd mailing list
# for helping me solve a number of compilation issues:
#   http://lists.puredata.info/pipermail/pd-list/2011-07/090103.html