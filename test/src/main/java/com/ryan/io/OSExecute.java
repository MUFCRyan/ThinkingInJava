package com.ryan.io;

import com.ryan.util.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by MUFCRyan on 2017/5/9.
 * Book Page550 Chapter 18.9 进程控制
 */

public class OSExecute {
    public static void command(String command){
        boolean error = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = results.readLine()) != null){
                Util.println(line);
            }
            // Report errors and return nonzero value to calling process if there are problems
            BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errors.readLine()) != null){
                Util.println(line);
                error = true;
            }
        } catch (Exception e){
            // Compensate for Windows 2000, which throws an exception for the default command line
            if (!command.startsWith("CMD /C"))
                command("CMD /C" + command);
            else
                throw new RuntimeException(e);
        }

        if (error){
            throw new OSExecuteException("Errors executing " + command);
        }
    }
}
