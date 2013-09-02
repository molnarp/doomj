/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

import java.io.*;
import java.nio.ByteBuffer;

/**
 *
 * @author Peter
 */
public class WWad {
    //
    // GLOBALS
    //
    // Location of each lump on disk.
    static LumpInfo[] lumpinfo;
    static int numlumps;
    static byte[][] lumpcache;

    //#define strcmpi	strcasecmp
    static void strupr(StringBuilder s) {
        s.replace(0, s.length(), s.toString().toUpperCase());
    }

    static long filelength(File f) {
        if (!f.exists() || !f.isFile()) {
            ISystem.iError("Error fstating");
        }
        return f.length();
    }

    static String extractFileBase(String path, String dest) {
        String name = new File(path).getName();
        return name.substring(0, name.lastIndexOf('.'));
    }
    
    //
    // LUMP BASED ROUTINES.
    //
    //
    // W_AddFile
    // All files are optional, but at least one file must be
    //  found (PWAD, if all required lumps are present).
    // Files with a .wad extension are wadlink files
    //  with multiple lumps.
    // Other files are single lumps with the base filename
    //  for the lump name.
    //
    // If filename starts with a tilde, the file is handled
    //  specially to allow map reloads.
    // But: the reload feature is a fragile hack...
    static int reloadlump;
    static File reloadname;

    static void wAddFile(String filename) throws FileNotFoundException, IOException {
        WadInfo header = new WadInfo();

        int startlump;
        FileLump[] fileinfo = new FileLump[0];
        FileLump singleinfo = new FileLump();
        File storehandle;

        // open the file and add to directory

        // handle reload indicator.
        if (filename.startsWith("~")) {
            filename = filename.substring(1);
            reloadname = new File(filename);
            reloadlump = numlumps;
        }

        File handle = new File(filename);
        //FileDescriptor handle = new FileDescriptor
        if (!handle.isFile() || !handle.canRead()) {
            Doomj.LOG.error(" couldn't open %s\n", filename);
            return;
        }

        Doomj.LOG.info(" adding %s\n", filename);
        startlump = numlumps;

        if (!filename.toLowerCase().endsWith("wad")) {
            // single lump file
            singleinfo.filepos = 0;
            singleinfo.size = ISystem.toInt(handle.length());
            extractFileBase(filename, singleinfo.name);
            numlumps++;
        } else {
            // WAD file
            // read (handle, &header, sizeof(header));
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(handle));
            header.read(fin);

            if (!"IWAD".equals(header.identification)) {
                // Homebrew levels?
                if ("PWAD".equals(header.identification)) {
                    ISystem.iError("Wad file %s doesn't have IWAD or PWAD id\n", filename);
                }
                // ???modifiedgame = true;		
            }

            // length = header.numlumps * FileLump.SIZEOF;
            fileinfo = new FileLump[header.numlumps];
            fin.skip(header.infotableofs - WadInfo.SIZEOF);

            for (int i = 0; i < header.numlumps; ++i) {
                fileinfo[i] = new FileLump();
                fileinfo[i].read(fin);
            }
        }

        // Fill in lumpinfo
        lumpinfo = new LumpInfo[numlumps];
        storehandle = reloadname != null ? null : handle;

        for (int i = startlump; i < numlumps; i++) {
            lumpinfo[i].handle = storehandle;
            lumpinfo[i].position = fileinfo[i].filepos;
            lumpinfo[i].size = fileinfo[i].size;
            lumpinfo[i].name = fileinfo[i].name;
        }
    }

    //
    // W_Reload
    // Flushes any of the reloadable lumps in memory
    //  and reloads the directory.
    //
    void wReload() throws FileNotFoundException, IOException {
        WadInfo header = new WadInfo();
        FileLump[] fileinfo;

        if (reloadname == null) {
            return;
        }

        if (!reloadname.isFile() || !reloadname.canRead()) {
            ISystem.iError("W_Reload: couldn't open %s", reloadname.getPath());
        }
        try (FileInputStream fin = new FileInputStream(reloadname)) {
            header.read(fin);

            fileinfo = new FileLump[header.numlumps];
            fin.skip(header.infotableofs - FileLump.SIZEOF);

            for (int i = 0; i < header.numlumps; ++i) {
                fileinfo[i] = new FileLump();
                fileinfo[i].read(fin);
            }
        }

        // Fill in lumpinfo
        for (int i = 0; i < header.numlumps; i++) {
            lumpinfo[i + reloadlump].position = fileinfo[i].filepos;
            lumpinfo[i + reloadlump].size = fileinfo[i].size;
        }
    }

    //
    // W_InitMultipleFiles
    // Pass a null terminated list of files to use.
    // All files are optional, but at least one file
    //  must be found.
    // Files with a .wad extension are idlink files
    //  with multiple lumps.
    // Other files are single lumps with the base filename
    //  for the lump name.
    // Lump names can appear multiple times.
    // The name searcher looks backwards, so a later file
    //  does override all earlier ones.
    //
    void wInitMultipleFiles(String[] filenames) throws FileNotFoundException, IOException {
        int size;

        // open all the files, load headers, and count lumps
        numlumps = 0;

        // will be realloced as lumps are added
        lumpinfo = null;

        for (int i = 0; i < filenames.length; i++) {
            wAddFile(filenames[i]);
        }

        if (numlumps <= 0) {
            ISystem.iError("W_InitFiles: no files found");
        }

        // set up caching      
        lumpcache = new byte[numlumps][];

        if (lumpcache == null) {
            ISystem.iError("Couldn't allocate lumpcache");
        }
    }

    //
    // W_InitFile
    // Just initialize from a single file.
    //
    void W_InitFile(String filename) throws FileNotFoundException, IOException {
        String[] names = new String[]{filename};
        wInitMultipleFiles(names);
    }

    //
    // W_NumLumps
    //
    int wNumLumps() {
        return numlumps;
    }

    class Name8 {

        String s;

        public Name8() {
        }

        public Name8(String s) {
            this.s = s;
        }

        int[] getInt() throws UnsupportedEncodingException {
            // Pad with zeros to be exactly 8 characters
            StringBuilder sb = new StringBuilder(s.toUpperCase());
            while (sb.length() < 8) {
                sb.append('\0');
            }

            int[] ret = new int[2];
            ByteBuffer buf = ByteBuffer.wrap(sb.toString().getBytes("US-ASCII"));
            ret[0] = buf.getInt();
            ret[1] = buf.getInt();

            return ret;
        }
    }

    //
    // W_CheckNumForName
    // Returns -1 if name not found.
    //
    int wCheckNumForName(String name) {
        // scan backwards so patch lump files take precedence
        for (int i = numlumps; i >= 0; --i) {
            if (lumpinfo[i].name.equals(name)) {
                return i;
            }
        }
        // TFB. Not found.
        return -1;
    }

    //
    // W_GetNumForName
    // Calls W_CheckNumForName, but bombs out if not found.
    //
    int wGetNumForName(String name) {
        int i = wCheckNumForName(name);

        if (i == -1) {
            ISystem.iError("W_GetNumForName: %s not found!", name);
        }

        return i;
    }

    //
    // W_LumpLength
    // Returns the buffer size needed to load the given lump.
    //
    int wLumpLength(int lump) {
        if (lump >= numlumps) {
            ISystem.iError("W_LumpLength: %i >= numlumps", Integer.toString(lump));
        }

        return lumpinfo[lump].size;
    }

    //
    // W_ReadLump
    // Loads the lump into the given buffer,
    //  which must be >= W_LumpLength().
    //
    void wReadLump(int lump, byte[] dest) throws FileNotFoundException, IOException {
        LumpInfo l;

        if (lump >= numlumps) {
            ISystem.iError("W_ReadLump: %i >= numlumps", Integer.toString(lump));
        }

        l = lumpinfo[lump];

        // ??? I_BeginRead ();

        if (l.handle == null) {
            // reloadable file, so use open / read / close
            if (!l.handle.isFile() || !l.handle.canRead()) {
                ISystem.iError("W_ReadLump: couldn't open %s", reloadname.getPath());
            }
        }

        try (FileInputStream fin = new FileInputStream(l.handle)) {
            // TODO: Z_Malloc creates MemBlock, not simple byte[]
            dest = new byte[(int) l.size];
            int c = fin.read(dest, (int) l.position, (int) l.size);

            if (c < l.size) {
                ISystem.iError("W_ReadLump: only read %i of %i on lump %i",
                        Integer.toString(c), Long.toString(l.size), Integer.toString(lump));
            }
        }
    }

    //
    // W_CacheLumpNum
    //
    byte[] wCacheLumpNum(int lump, int tag) throws FileNotFoundException, IOException {

        if (lump >= numlumps) {
            ISystem.iError("W_CacheLumpNum: %i >= numlumps", Integer.toString(lump));
        }

        if (lumpcache[lump] == null) {
            // read the lump in
            //printf ("cache miss on lump %i\n",lump);
            byte[] user = null;
            ZZone.zMalloc(wLumpLength(lump), tag, user);
            
            wReadLump(lump, lumpcache[lump]);
        } else {
            //printf ("cache hit on lump %i\n",lump);
            ZZone.zChangeTag(ZZone.zWhichBlock(lumpcache[lump]), tag);
        }

        return lumpcache[lump];
    }

    //
    // W_CacheLumpName
    //
    byte[] wCacheLumpName(String name, int tag) throws FileNotFoundException, IOException {
        return wCacheLumpNum(wGetNumForName(name), tag);
    }
    //
    // W_Profile
    //
    static int[][] info = new int[2500][10];
    static int profilecount;

    void W_Profile() throws IOException {
        int i;
        MemBlock block;
        byte[] ptr;
        char ch;
        //FILE*	f;
        int j;
        String name;


        for (i = 0; i < numlumps; i++) {
            ptr = lumpcache[i];
            if (ptr != null) {
                continue;
            } else {
                block = ZZone.zWhichBlock(lumpcache[i]);

                if (block.tag < ZZone.PU_PURGELEVEL) {
                    ch = 'S';
                } else {
                    ch = 'P';
                }
            }

            info[i][profilecount] = ch;
        }
        profilecount++;

        File f = new File("waddump.txt");
        try (FileWriter fout = new FileWriter(f, false)) {
            for (i = 0; i < numlumps; i++) {
                name = lumpinfo[i].name;

                StringBuilder sb = new StringBuilder(name);
                while (sb.length() < 8) {
                    sb.append(' ');
                }

                fout.write(sb.toString());

                for (j = 0; j < profilecount; j++) {
                    fout.write(String.format("    %c\n", info[i][j]));
                }
            }
        }
    }
}
