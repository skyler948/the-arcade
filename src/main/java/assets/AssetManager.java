package assets;

import java.util.HashMap;

import static com.raylib.Raylib.*;

public class AssetManager {

    private final String ROOT = "resources/";

    private final String TEXTURE_PATH = "textures/";
    private final String SOUND_PATH = "sounds/";
    private final String MUSIC_PATH = "music/";
    private final String SHADER_PATH = "shaders/";
    private final String FONT_PATH = "fonts/";

    private final String TEXTURE_FORMAT = ".png";
    private final String SOUND_FORMAT = ".wav";
    private final String MUSIC_FORMAT = ".mp3";
    private final String FONT_FORMAT = ".ttf";

    private HashMap<String, Texture> textures;
    private HashMap<String, Sound> sounds;
    private HashMap<String, Music> music;
    private HashMap<String[], Shader> shaders;
    private HashMap<String, Font> fonts;

    public AssetManager() {
        textures = new HashMap<>();
        sounds = new HashMap<>();
        music = new HashMap<>();
        shaders = new HashMap<>();
        fonts = new HashMap<>();
    }

    public Texture getTexture(String path) {
        return textures.get(ROOT + TEXTURE_PATH + path + TEXTURE_FORMAT);
    }

    public void loadTexture(String path) {
        textures.put(ROOT + TEXTURE_PATH + path + TEXTURE_FORMAT,
                LoadTexture(ROOT + TEXTURE_PATH + path + TEXTURE_FORMAT));
    }

    public void unloadTexture(String path) {
        UnloadTexture(textures.get(ROOT + TEXTURE_PATH + path + TEXTURE_FORMAT));
        textures.remove(ROOT + TEXTURE_PATH + path + TEXTURE_FORMAT);
    }

    public void unloadTextures() {
        for (Texture texture : textures.values()) {
            UnloadTexture(texture);
        }
        textures.clear();
    }

    public Sound getSound(String path) {
        return sounds.get(ROOT + SOUND_PATH + path + SOUND_FORMAT);
    }

    public void loadSound(String path) {
        sounds.put(ROOT + SOUND_PATH + path + SOUND_FORMAT,
                LoadSound(ROOT + SOUND_PATH + path + SOUND_FORMAT));
    }

    public void unloadSound(String path) {
        UnloadSound(sounds.get(ROOT + SOUND_PATH + path + SOUND_FORMAT));
        sounds.remove(ROOT + SOUND_PATH + path + SOUND_FORMAT);
    }

    public void unloadSounds() {
        for (Sound sound : sounds.values()) {
            UnloadSound(sound);
        }
        sounds.clear();
    }

    public Music getMusic(String path) {
        return music.get(ROOT + MUSIC_PATH + path + MUSIC_FORMAT);
    }

    public void loadMusic(String path) {
        music.put(ROOT + MUSIC_PATH + path + MUSIC_FORMAT,
                LoadMusicStream(ROOT + MUSIC_PATH + path + MUSIC_FORMAT));
    }

    public void unloadMusic(String path) {
        UnloadMusicStream(music.get(ROOT + MUSIC_PATH + path + MUSIC_FORMAT));
        music.remove(ROOT + MUSIC_PATH + path + MUSIC_FORMAT);
    }

    public void unloadMusic() {
        for (Music m : music.values()) {
            UnloadMusicStream(m);
        }
        music.clear();
    }

    public Shader getShader(String vs, String fs) {
        return shaders.get(new String[]{ROOT + SHADER_PATH + vs, ROOT + SHADER_PATH + fs});
    }

    public void loadShader(String vs, String fs) {
        shaders.put(new String[]{ROOT + SHADER_PATH + vs, ROOT + SHADER_PATH + fs},
                LoadShader(ROOT + SHADER_PATH + vs, ROOT + SHADER_PATH + fs));
    }

    public void unloadShaders() {
        for (Shader shader : shaders.values()) {
            UnloadShader(shader);
        }
        shaders.clear();
    }

    public Font getFont(String path) {
        return fonts.get(ROOT + FONT_PATH + path + FONT_FORMAT);
    }

    public void loadFont(String path) {
        fonts.put(ROOT + FONT_PATH + path + FONT_FORMAT,
                LoadFont(ROOT + FONT_PATH + path + FONT_FORMAT));
    }

    public void unloadFont(String path) {
        UnloadFont(fonts.get(ROOT + FONT_PATH + path + FONT_FORMAT));
        fonts.remove(ROOT + FONT_PATH + path + FONT_FORMAT);
    }

    public void unloadFonts() {
        for (Font font : fonts.values()) {
            UnloadFont(font);
        }
        fonts.clear();
    }

    public void unloadAssets() {
        unloadTextures();
        unloadSounds();
        unloadMusic();
        unloadShaders();
        unloadFonts();
    }

    public HashMap<String, Sound> getSounds() {
        return sounds;
    }

    public HashMap<String, Music> getMusic() {
        return music;
    }

}
