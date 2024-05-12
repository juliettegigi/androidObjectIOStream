package com.softulp.app.tp2objectiostream.request;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import com.softulp.app.tp2objectiostream.models.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ApiClient {
    private static String nombreArchivo;
    static {
        nombreArchivo="archivo1.dat";
    }

    public static void guardar(Context context, Usuario user){

        File archivo=new File(context.getFilesDir(),nombreArchivo);
        ObjectOutputStream oos = null;
        try {

            FileOutputStream fos = new FileOutputStream(archivo);
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            oos=new ObjectOutputStream(bos);
            oos.writeObject(user);
            bos.flush();
            oos.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(context,"Error al acceder al archivo",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context,"Error al acceder al archivo",Toast.LENGTH_LONG).show();
        }finally { if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }

    }

    public static Usuario leer(Context context){
        Usuario user=null;
        File archive=new File(context.getFilesDir(),nombreArchivo);
        FileInputStream fis=null;
        try {
            fis=new FileInputStream(archive);
            BufferedInputStream bis=new BufferedInputStream(fis);
            ObjectInputStream ois=new ObjectInputStream(bis);
            user = (Usuario) ois.readObject();

        } catch (FileNotFoundException e) {
            // el archivo no existe
            Toast.makeText(context,"Error de E/s noyfoud",Toast.LENGTH_LONG).show();
        } catch (EOFException e) {
            //el archivo está vacío
            Toast.makeText(context,"Error de E/s noyfoud",Toast.LENGTH_LONG).show();
        }

        catch (IOException e) {
            Toast.makeText(context,"Error de E/s",Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            Toast.makeText(context,"Error al recuperar datos",Toast.LENGTH_LONG).show();
        }
        finally {
            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {

                }
            }
        }
        return user;

    }

    public static  Usuario login(Context context,String email,String pass){
        Usuario usuario=leer(context);
        if(usuario!=null && usuario.getDni()!=-1 && usuario.getEmail().equals(email) && usuario.getPass().equals(pass)){
            return usuario;
        }
        return null;
    }


}
