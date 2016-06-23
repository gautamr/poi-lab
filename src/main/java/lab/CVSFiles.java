package lab;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public class CVSFiles {
    
    private static final String CVS_LIST = "cvs_list.txt";
    private static final String PREFIX = "/home/secure/services/cvsroot/finch/finchrepo/finch";
    private static final String SUFFIX = ",v  <--";

    public static void main(String[] args) throws IOException {
        ImmutableList<String> files = Files.readLines(new File(CVS_LIST),
            Charsets.UTF_8, 
            new LineProcessor<ImmutableList<String>>() {
                final ImmutableList.Builder<String> bldr = ImmutableList.builder();
                @Override
                public boolean processLine(String line) {
                    if (line.contains("<--"))
                        bldr.add(line.substring(PREFIX.length() - 1, line.indexOf(SUFFIX)));
                    else if (line.contains("revision:"))
                        bldr.add(line.substring(line.indexOf("revision:")));
                    return true;
                }

                @Override
                public ImmutableList<String> getResult() {
                    return bldr.build();
                }
            });
        for (int i = 0; i < files.size(); i++) {
            if (i % 2 == 0) {
                String status;
                if (files.get(i + 1).contains("del")) status = "[DEL]: ";
                else if (files.get(i + 1).contains("previous")) status = "[MOD]: ";
                else status = "[NEW]: ";
                System.out.print(status + files.get(i));
            }
            else System.out.println(" -- " + files.get(i));
        }
    }
}
