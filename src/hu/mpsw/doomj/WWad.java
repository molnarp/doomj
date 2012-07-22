/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

import java.io.*;

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

  // TODO??? void**			lumpcache;


  //#define strcmpi	strcasecmp

  static void strupr(StringBuilder s) {
    s.replace(0, s.length(), s.toString().toUpperCase());
  }

  static long filelength(File f) {
    if (! f.exists() || ! f.isFile()) {
      ISystem.iError("Error fstating");
    }
    return f.length();
  }


  static String ExtractFileBase (String path, String dest) {
    String name = new File(path).getName();
    return name.substring(0, name.lastIndexOf('.') - 1);
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
    WadInfo	header = new WadInfo();

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
    if (! handle.isFile() || ! handle.canRead()) {
      System.err.printf(" couldn't open %s\n",filename);
      return;
    }

    System.err.printf(" adding %s\n", filename);
    startlump = numlumps;

    if (! filename.toLowerCase().endsWith("wad")) {
      // single lump file
      singleinfo.filepos = 0;
      singleinfo.size = handle.length();
      ExtractFileBase(filename, singleinfo.name);
      numlumps++;
    } else {
      // WAD file
      // read (handle, &header, sizeof(header));
      FileInputStream fin = new FileInputStream(handle);        
      header.read(fin);

      if (! "IWAD".equals(header.identification)) {
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
    int lumpcount;
    LumpInfo lump_p;
    File handle;
    int length;
    FileLump[] fileinfo;

    if (reloadname == null) {
      return;
    }

    if (! reloadname.isFile() || ! reloadname.canRead()) {
      ISystem.iError("W_Reload: couldn't open %s", reloadname.getPath());
    }

    FileInputStream fin = new FileInputStream(reloadname);
    header.read(fin);
    
    fileinfo = new FileLump[header.numlumps];
    fin.skip(header.infotableofs - FileLump.SIZEOF);
    
    for (int i = 0; i < header.numlumps; ++i) {
      fileinfo[i] = new FileLump();
      fileinfo[i].read(fin);
    }

    // Fill in lumpinfo
    lump_p = &lumpinfo[reloadlump];

    for (i=reloadlump ; i<reloadlump+lumpcount ; i++,lump_p++, fileinfo++) {
      if (lumpcache[i])
          Z_Free (lumpcache[i]);

      lump_p->position = LONG(fileinfo->filepos);
      lump_p->size = LONG(fileinfo->size);
    }

    close (handle);
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
  void W_InitMultipleFiles (char** filenames)
  {	
      int		size;

      // open all the files, load headers, and count lumps
      numlumps = 0;

      // will be realloced as lumps are added
      lumpinfo = malloc(1);	

      for ( ; *filenames ; filenames++)
    W_AddFile (*filenames);

      if (!numlumps)
    I_Error ("W_InitFiles: no files found");

      // set up caching
      size = numlumps * sizeof(*lumpcache);
      lumpcache = malloc (size);

      if (!lumpcache)
    I_Error ("Couldn't allocate lumpcache");

      memset (lumpcache,0, size);
  }




  //
  // W_InitFile
  // Just initialize from a single file.
  //
  void W_InitFile (char* filename)
  {
      char*	names[2];

      names[0] = filename;
      names[1] = NULL;
      W_InitMultipleFiles (names);
  }



  //
  // W_NumLumps
  //
  int W_NumLumps (void)
  {
      return numlumps;
  }



  //
  // W_CheckNumForName
  // Returns -1 if name not found.
  //

  int W_CheckNumForName (char* name)
  {
      union {
    char	s[9];
    int	x[2];

      } name8;

      int		v1;
      int		v2;
      lumpinfo_t*	lump_p;

      // make the name into two integers for easy compares
      strncpy (name8.s,name,8);

      // in case the name was a fill 8 chars
      name8.s[8] = 0;

      // case insensitive
      strupr (name8.s);		

      v1 = name8.x[0];
      v2 = name8.x[1];


      // scan backwards so patch lump files take precedence
      lump_p = lumpinfo + numlumps;

      while (lump_p-- != lumpinfo)
      {
    if ( *(int *)lump_p->name == v1
        && *(int *)&lump_p->name[4] == v2)
    {
        return lump_p - lumpinfo;
    }
      }

      // TFB. Not found.
      return -1;
  }




  //
  // W_GetNumForName
  // Calls W_CheckNumForName, but bombs out if not found.
  //
  int W_GetNumForName (char* name)
  {
      int	i;

      i = W_CheckNumForName (name);

      if (i == -1)
        I_Error ("W_GetNumForName: %s not found!", name);

      return i;
  }


  //
  // W_LumpLength
  // Returns the buffer size needed to load the given lump.
  //
  int W_LumpLength (int lump)
  {
      if (lump >= numlumps)
    I_Error ("W_LumpLength: %i >= numlumps",lump);

      return lumpinfo[lump].size;
  }



  //
  // W_ReadLump
  // Loads the lump into the given buffer,
  //  which must be >= W_LumpLength().
  //
  void
  W_ReadLump
  ( int		lump,
    void*		dest )
  {
      int		c;
      lumpinfo_t*	l;
      int		handle;

      if (lump >= numlumps)
    I_Error ("W_ReadLump: %i >= numlumps",lump);

      l = lumpinfo+lump;

      // ??? I_BeginRead ();

      if (l->handle == -1)
      {
    // reloadable file, so use open / read / close
    if ( (handle = open (reloadname,O_RDONLY | O_BINARY)) == -1)
        I_Error ("W_ReadLump: couldn't open %s",reloadname);
      }
      else
    handle = l->handle;

      lseek (handle, l->position, SEEK_SET);
      c = read (handle, dest, l->size);

      if (c < l->size)
    I_Error ("W_ReadLump: only read %i of %i on lump %i",
      c,l->size,lump);	

      if (l->handle == -1)
    close (handle);

      // ??? I_EndRead ();
  }




  //
  // W_CacheLumpNum
  //
  void*
  W_CacheLumpNum
  ( int		lump,
    int		tag )
  {
      byte*	ptr;

      if ((unsigned)lump >= numlumps)
    I_Error ("W_CacheLumpNum: %i >= numlumps",lump);

      if (!lumpcache[lump])
      {
    // read the lump in

    //printf ("cache miss on lump %i\n",lump);
    ptr = Z_Malloc (W_LumpLength (lump), tag, &lumpcache[lump]);
    W_ReadLump (lump, lumpcache[lump]);
      }
      else
      {
    //printf ("cache hit on lump %i\n",lump);
    Z_ChangeTag (lumpcache[lump],tag);
      }

      return lumpcache[lump];
  }



  //
  // W_CacheLumpName
  //
  void*
  W_CacheLumpName
  ( char*		name,
    int		tag )
  {
      return W_CacheLumpNum (W_GetNumForName(name), tag);
  }


  //
  // W_Profile
  //
  int		info[2500][10];
  int		profilecount;

  void W_Profile (void)
  {
      int		i;
      memblock_t*	block;
      void*	ptr;
      char	ch;
      FILE*	f;
      int		j;
      char	name[9];


      for (i=0 ; i<numlumps ; i++)
      {	
    ptr = lumpcache[i];
    if (!ptr)
    {
        ch = ' ';
        continue;
    }
    else
    {
        block = (memblock_t *) ( (byte *)ptr - sizeof(memblock_t));
        if (block->tag < PU_PURGELEVEL)
      ch = 'S';
        else
      ch = 'P';
    }
    info[i][profilecount] = ch;
      }
      profilecount++;

      f = fopen ("waddump.txt","w");
      name[8] = 0;

      for (i=0 ; i<numlumps ; i++)
      {
    memcpy (name,lumpinfo[i].name,8);

    for (j=0 ; j<8 ; j++)
        if (!name[j])
      break;

    for ( ; j<8 ; j++)
        name[j] = ' ';

    fprintf (f,"%s ",name);

    for (j=0 ; j<profilecount ; j++)
        fprintf (f,"    %c",info[i][j]);

    fprintf (f,"\n");
      }
      fclose (f);
  }
  
}
