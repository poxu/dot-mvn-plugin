package poxu.plugin;

import org.jetbrains.idea.maven.execution.MavenRunnerSettings;
import org.jetbrains.idea.maven.server.MavenServerManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class MavenOpts {
    private final String opts;

    public MavenOpts(String opts) {
        this.opts = opts;
    }

    public MavenOpts(File jvmConfig) {
        final List<String> collect;
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(jvmConfig)))) {
            collect = bufferedReader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error opening jvm config file", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.opts = String.join(" ", collect);
    }

    public void embed(MavenRunnerSettings mavenGeneralSettings) {
        mavenGeneralSettings.setVmOptions(opts);
    }

    public void embed(MavenServerManager mavenServerManager) {
        mavenServerManager.setMavenEmbedderVMOptions(opts);
    }
}
