package poxu.plugin;

import org.jetbrains.idea.maven.execution.MavenRunnerSettings;
import org.jetbrains.idea.maven.server.MavenServerManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class MavenOptsTest {
    @Test
    void embedRunnerSettings() {
        final MavenRunnerSettings mavenRunnerSettings = new MavenRunnerSettings();
        mavenRunnerSettings.setVmOptions("");
        final String line = "-Doption1=value1 -Doption2=value2";
        final MavenOpts mavenOpts = new MavenOpts(line);
        mavenOpts.embed(mavenRunnerSettings);

        assertEquals(line, mavenRunnerSettings.getVmOptions());
    }

    @Test
    void embedImporterSettings() {
        final MavenServerManager mavenServerManager = new MavenServerManager();
        mavenServerManager.setMavenEmbedderVMOptions("");
        final String line = "-Doption1=value1 -Doption2=value2";
        final MavenOpts mavenOpts = new MavenOpts(line);
        mavenOpts.embed(mavenServerManager);

        assertEquals(line, mavenServerManager.getMavenEmbedderVMOptions());
    }

    @Test
    void embedImporterSettingsFromFile() {
        final URL resource = getClass().getResource("jvm.config");
        final File file = new File(resource.getFile());
        final MavenServerManager mavenServerManager = new MavenServerManager();
        mavenServerManager.setMavenEmbedderVMOptions("");
        final String line = "-Doption1=value1 -Doption2=value2";
        final MavenOpts mavenOpts = new MavenOpts(file);
        mavenOpts.embed(mavenServerManager);

        assertEquals(line, mavenServerManager.getMavenEmbedderVMOptions());
    }
}
