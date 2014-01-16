package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {
	/**
     * Zipファイルを展開します
     * @param aZipFile zipファイル
     * @param aOutDir  出力先ディレクトリ
     */
    public void decode( File aZipFile, String aOutDir ){
        FileInputStream  fileIn  = null;
        FileOutputStream fileOut = null;
        ZipInputStream zipIn = null;
        try{
            //-------------------------------
            // 出力先ディレクトリを作る
            //-------------------------------
            File outDir = new File( aOutDir );
            outDir.mkdirs();
            
            //-------------------------------
            // zipファイルをオープンする 
            //-------------------------------
            fileIn = new FileInputStream( aZipFile );
            zipIn = new ZipInputStream( fileIn );
            
            ZipEntry entry = null;
            while( ( entry = zipIn.getNextEntry() ) != null ){
                if( entry.isDirectory() ){
                    //------------------------------
                    // ディレクトリだった場合は、
                    // 出力先ディレクトリを作成する
                    //------------------------------
                    String relativePath = entry.getName();
                    outDir = new File( outDir, relativePath );
                    outDir.mkdirs();
                    
                } else {
                    //------------------------------
                    // ファイルの場合は出力する
                    // 出力先は、現在の outDirの下
                    //------------------------------
                    String relativePath = entry.getName();
                    File   outFile = new File( outDir, relativePath );
                    
                    // 出力先のディレクトリを作成する
                    File   parentFile = outFile.getParentFile();
                    parentFile.mkdirs();
                    
                    // ファイルを出力する
                    fileOut = new FileOutputStream( outFile );
                    
                    byte[] buf  = new byte[ 256 ];
                    int    size = 0;
                    while( ( size = zipIn.read( buf ) ) > 0 ){
                        fileOut.write( buf, 0, size );
                    }
                    fileOut.close();
                    fileOut = null;
                }
                zipIn.closeEntry();
            }
            
        }catch( Exception e){
            e.printStackTrace();
            
        } finally {
            //----------------------------
            // 後始末
            //----------------------------
            if( fileIn != null ){
                try{
                    fileIn.close();
                }catch( Exception e){}
            }
            if( fileOut != null ){
                try{
                    fileOut.close();
                }catch( Exception e){}
            }
            if( zipIn !=null){
            	try {
					zipIn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
            }
            
        }
    }
}
