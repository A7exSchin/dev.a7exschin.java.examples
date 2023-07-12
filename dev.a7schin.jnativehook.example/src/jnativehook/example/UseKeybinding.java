package jnativehook.example;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

@Component(immediate = true)
public class UseKeybinding implements NativeKeyListener {

	private short hotKeyFlag = 0x00;
	private static final short MASK_ALT = 1 << 0;
	private static final short MASK_D = 1 << 1;
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		try {
			switch (e.getKeyCode()) {
				case NativeKeyEvent.VC_ESCAPE -> GlobalScreen.unregisterNativeHook();
				case NativeKeyEvent.VC_ALT -> hotKeyFlag ^= MASK_ALT;
				case NativeKeyEvent.VC_D -> hotKeyFlag ^= MASK_D;
				default -> hotKeyFlag = 0x00;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		if (hotKeyFlag == (MASK_ALT ^ MASK_D)) {
			System.out.println("Shortcut triggered :)");
			hotKeyFlag = 0x00;
		} else {
			hotKeyFlag = 0x00;
		}
	}

	@Activate
	public static void activate() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new UseKeybinding());
	}

	public static void main(String[] args) {
		activate();
	}
}
