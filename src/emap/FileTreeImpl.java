package emap;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileTreeImpl implements FT{

    @Override
    public Optional<String> tree(Path path) {
        File file = new File(String.valueOf(path));
        if ( !file.exists()) return Optional.empty();
        if ( file.isFile()) {
            return Optional.of(file.getName() + " " + file.length() + " bytes");
        }
        if (file.isDirectory()) {

            return Optional.of(directoryTree(file, new ArrayList<>()));
        }
        return Optional.empty();
    }
    private String directoryTree(File folder, List<Boolean> lastFolders) {
        StringBuilder directory = new StringBuilder();
        if (lastFolders.size() != 0)
            directory.append(!(lastFolders.get(lastFolders.size() - 1)) ? "├─ " : "└─ ");
        directory.append(folder.getName()).append(" ").append(folderSize(folder));

        File[] files = folder.listFiles();
        assert files != null;
        int count = files.length;
        Arrays.sort(files,(File a,File b ) -> {
            int result = Boolean.compare(a.isFile(), b.isFile());
            if (result == 0){
                result = a.getName().compareToIgnoreCase(b.getName());

            }
            return result;
        });
        for (int i = 0; i < count; i++) {
            directory.append("\n");
            for (Boolean lastFolder : lastFolders) {
                if (lastFolder) {
                    directory.append("   ");
                } else {
                    directory.append("│  ");
                }
            }
            if (files[i].isFile()) {
                directory.append(i + 1 == count ? "└" : "├").append("─ ").append(files[i].getName()).append(" ").append(files[i].length()).append(" bytes");
            } else {
                ArrayList<Boolean> list = new ArrayList<>(lastFolders);
                list.add(i+1 == count);
                directory.append(directoryTree(files[i], list));
            }
        }
        return directory.toString();
    }
    private long getFolderSize(File folder) {
        long size = 0;
        File[] files = folder.listFiles();

        assert files != null;
        int count = files.length;

        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }
        return size;
    }
    private String folderSize(File folder) {
        return getFolderSize(folder) + " bytes";
    }

            /*Arrays.sort(folder);
            List<File> sorted = new ArrayList<>();

            for (File file : folder) {
                if (file.isDirectory()) sorted.add(file);
            }

            for (File file : folder) {
                if (file.isFile()) sorted.add(file);
            }
            return sorted.toArray(new File[0]);

           if(Files.notExists(path))
                return Optional.empty();
            if(Files.isRegularFile(path)) {
                try {
                    long bytes = Files.size(path);
                    return Optional.of(path.getFileName().toString() +  " " + bytes +  " bytes");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;*/

}
