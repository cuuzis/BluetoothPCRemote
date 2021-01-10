package com.example.gustavs.remotepccontroller.connection;

public enum WindowsKey {

    //Value not used by microsoft:
    EMPTY((byte)0),

    //
    // Summary:
    //     Left mouse button
    LBUTTON((byte)1),
    //
    // Summary:
    //     Right mouse button
    RBUTTON((byte)2),
    //
    // Summary:
    //     Control-break processing
    CANCEL((byte)3),
    //
    // Summary:
    //     Middle mouse button (three-button mouse) - NOT contiguous with LBUTTON and RBUTTON
    MBUTTON((byte)4),
    //
    // Summary:
    //     Windows 2000/XP: X1 mouse button - NOT contiguous with LBUTTON and RBUTTON
    XBUTTON1((byte)5),
    //
    // Summary:
    //     Windows 2000/XP: X2 mouse button - NOT contiguous with LBUTTON and RBUTTON
    XBUTTON2((byte)6),
    //
    // Summary:
    //     BACKSPACE key
    BACK((byte)8),
    //
    // Summary:
    //     TAB key
    TAB((byte)9),
    //
    // Summary:
    //     CLEAR key
    CLEAR((byte)12),
    //
    // Summary:
    //     ENTER key
    RETURN((byte)13),
    //
    // Summary:
    //     SHIFT key
    SHIFT((byte)16),
    //
    // Summary:
    //     CTRL key
    CONTROL((byte)17),
    //
    // Summary:
    //     ALT key
    MENU((byte)18),
    //
    // Summary:
    //     PAUSE key
    PAUSE((byte)19),
    //
    // Summary:
    //     CAPS LOCK key
    CAPITAL((byte)20),
    //
    // Summary:
    //     Input Method Editor (IME) Kana mode
    KANA((byte)21),
    //
    // Summary:
    //     IME Hanguel mode (maintained for compatibility; use HANGUL)
    HANGEUL((byte)21),
    //
    // Summary:
    //     IME Hangul mode
    HANGUL((byte)21),
    //
    // Summary:
    //     IME Junja mode
    JUNJA((byte)23),
    //
    // Summary:
    //     IME final mode
    FINAL((byte)24),
    //
    // Summary:
    //     IME Hanja mode
    HANJA((byte)25),
    //
    // Summary:
    //     IME Kanji mode
    KANJI((byte)25),
    //
    // Summary:
    //     ESC key
    ESCAPE((byte)27),
    //
    // Summary:
    //     IME convert
    CONVERT((byte)28),
    //
    // Summary:
    //     IME nonconvert
    NONCONVERT((byte)29),
    //
    // Summary:
    //     IME accept
    ACCEPT((byte)30),
    //
    // Summary:
    //     IME mode change request
    MODECHANGE((byte)31),
    //
    // Summary:
    //     SPACEBAR
    SPACE((byte)32),
    //
    // Summary:
    //     PAGE UP key
    PRIOR((byte)33),
    //
    // Summary:
    //     PAGE DOWN key
    NEXT((byte)34),
    //
    // Summary:
    //     END key
    END((byte)35),
    //
    // Summary:
    //     HOME key
    HOME((byte)36),
    //
    // Summary:
    //     LEFT ARROW key
    LEFT((byte)37),
    //
    // Summary:
    //     UP ARROW key
    UP((byte)38),
    //
    // Summary:
    //     RIGHT ARROW key
    RIGHT((byte)39),
    //
    // Summary:
    //     DOWN ARROW key
    DOWN((byte)40),
    //
    // Summary:
    //     SELECT key
    SELECT((byte)41),
    //
    // Summary:
    //     PRINT key
    PRINT((byte)42),
    //
    // Summary:
    //     EXECUTE key
    EXECUTE((byte)43),
    //
    // Summary:
    //     PRINT SCREEN key
    SNAPSHOT((byte)44),
    //
    // Summary:
    //     INS key
    INSERT((byte)45),
    //
    // Summary:
    //     DEL key
    DELETE((byte)46),
    //
    // Summary:
    //     HELP key
    HELP((byte)47),
    //
    // Summary:
    //     0 key
    VK_0((byte)48),
    //
    // Summary:
    //     1 key
    VK_1((byte)49),
    //
    // Summary:
    //     2 key
    VK_2((byte)50),
    //
    // Summary:
    //     3 key
    VK_3((byte)51),
    //
    // Summary:
    //     4 key
    VK_4((byte)52),
    //
    // Summary:
    //     5 key
    VK_5((byte)53),
    //
    // Summary:
    //     6 key
    VK_6((byte)54),
    //
    // Summary:
    //     7 key
    VK_7((byte)55),
    //
    // Summary:
    //     8 key
    VK_8((byte)56),
    //
    // Summary:
    //     9 key
    VK_9((byte)57),
    //
    // Summary:
    //     A key
    VK_A((byte)65),
    //
    // Summary:
    //     B key
    VK_B((byte)66),
    //
    // Summary:
    //     C key
    VK_C((byte)67),
    //
    // Summary:
    //     D key
    VK_D((byte)68),
    //
    // Summary:
    //     E key
    VK_E((byte)69),
    //
    // Summary:
    //     F key
    VK_F((byte)70),
    //
    // Summary:
    //     G key
    VK_G((byte)71),
    //
    // Summary:
    //     H key
    VK_H((byte)72),
    //
    // Summary:
    //     I key
    VK_I((byte)73),
    //
    // Summary:
    //     J key
    VK_J((byte)74),
    //
    // Summary:
    //     K key
    VK_K((byte)75),
    //
    // Summary:
    //     L key
    VK_L((byte)76),
    //
    // Summary:
    //     M key
    VK_M((byte)77),
    //
    // Summary:
    //     N key
    VK_N((byte)78),
    //
    // Summary:
    //     O key
    VK_O((byte)79),
    //
    // Summary:
    //     P key
    VK_P((byte)80),
    //
    // Summary:
    //     Q key
    VK_Q((byte)81),
    //
    // Summary:
    //     R key
    VK_R((byte)82),
    //
    // Summary:
    //     S key
    VK_S((byte)83),
    //
    // Summary:
    //     T key
    VK_T((byte)84),
    //
    // Summary:
    //     U key
    VK_U((byte)85),
    //
    // Summary:
    //     V key
    VK_V((byte)86),
    //
    // Summary:
    //     W key
    VK_W((byte)87),
    //
    // Summary:
    //     X key
    VK_X((byte)88),
    //
    // Summary:
    //     Y key
    VK_Y((byte)89),
    //
    // Summary:
    //     Z key
    VK_Z((byte)90),
    //
    // Summary:
    //     Left Windows key (Microsoft Natural keyboard)
    LWIN((byte)91),
    //
    // Summary:
    //     Right Windows key (Natural keyboard)
    RWIN((byte)92),
    //
    // Summary:
    //     Applications key (Natural keyboard)
    APPS((byte)93),
    //
    // Summary:
    //     Computer Sleep key
    SLEEP((byte)95),
    //
    // Summary:
    //     Numeric keypad 0 key
    NUMPAD0((byte)96),
    //
    // Summary:
    //     Numeric keypad 1 key
    NUMPAD1((byte)97),
    //
    // Summary:
    //     Numeric keypad 2 key
    NUMPAD2((byte)98),
    //
    // Summary:
    //     Numeric keypad 3 key
    NUMPAD3((byte)99),
    //
    // Summary:
    //     Numeric keypad 4 key
    NUMPAD4((byte)100),
    //
    // Summary:
    //     Numeric keypad 5 key
    NUMPAD5((byte)101),
    //
    // Summary:
    //     Numeric keypad 6 key
    NUMPAD6((byte)102),
    //
    // Summary:
    //     Numeric keypad 7 key
    NUMPAD7((byte)103),
    //
    // Summary:
    //     Numeric keypad 8 key
    NUMPAD8((byte)104),
    //
    // Summary:
    //     Numeric keypad 9 key
    NUMPAD9((byte)105),
    //
    // Summary:
    //     Multiply key
    MULTIPLY((byte)106),
    //
    // Summary:
    //     Add key
    ADD((byte)107),
    //
    // Summary:
    //     Separator key
    SEPARATOR((byte)108),
    //
    // Summary:
    //     Subtract key
    SUBTRACT((byte)109),
    //
    // Summary:
    //     Decimal key
    DECIMAL((byte)110),
    //
    // Summary:
    //     Divide key
    DIVIDE((byte)111),
    //
    // Summary:
    //     F1 key
    F1((byte)112),
    //
    // Summary:
    //     F2 key
    F2((byte)113),
    //
    // Summary:
    //     F3 key
    F3((byte)114),
    //
    // Summary:
    //     F4 key
    F4((byte)115),
    //
    // Summary:
    //     F5 key
    F5((byte)116),
    //
    // Summary:
    //     F6 key
    F6((byte)117),
    //
    // Summary:
    //     F7 key
    F7((byte)118),
    //
    // Summary:
    //     F8 key
    F8((byte)119),
    //
    // Summary:
    //     F9 key
    F9((byte)120),
    //
    // Summary:
    //     F10 key
    F10((byte)121),
    //
    // Summary:
    //     F11 key
    F11((byte)122),
    //
    // Summary:
    //     F12 key
    F12((byte)123),
    //
    // Summary:
    //     F13 key
    F13((byte)124),
    //
    // Summary:
    //     F14 key
    F14((byte)125),
    //
    // Summary:
    //     F15 key
    F15((byte)126),
    //
    // Summary:
    //     F16 key
    F16((byte)127),
    //
    // Summary:
    //     F17 key
    F17((byte)128),
    //
    // Summary:
    //     F18 key
    F18((byte)129),
    //
    // Summary:
    //     F19 key
    F19((byte)130),
    //
    // Summary:
    //     F20 key
    F20((byte)131),
    //
    // Summary:
    //     F21 key
    F21((byte)132),
    //
    // Summary:
    //     F22 key
    F22((byte)133),
    //
    // Summary:
    //     F23 key
    F23((byte)134),
    //
    // Summary:
    //     F24 key
    F24((byte)135),
    //
    // Summary:
    //     NUM LOCK key
    NUMLOCK((byte)144),
    //
    // Summary:
    //     SCROLL LOCK key
    SCROLL((byte)145),
    //
    // Summary:
    //     Left SHIFT key - Used only as parameters to GetAsyncKeyState() and GetKeyState()
    LSHIFT((byte)160),
    //
    // Summary:
    //     Right SHIFT key - Used only as parameters to GetAsyncKeyState() and GetKeyState()
    RSHIFT((byte)161),
    //
    // Summary:
    //     Left CONTROL key - Used only as parameters to GetAsyncKeyState() and GetKeyState()
    LCONTROL((byte)162),
    //
    // Summary:
    //     Right CONTROL key - Used only as parameters to GetAsyncKeyState() and GetKeyState()
    RCONTROL((byte)163),
    //
    // Summary:
    //     Left MENU key - Used only as parameters to GetAsyncKeyState() and GetKeyState()
    LMENU((byte)164),
    //
    // Summary:
    //     Right MENU key - Used only as parameters to GetAsyncKeyState() and GetKeyState()
    RMENU((byte)165),
    //
    // Summary:
    //     Windows 2000/XP: Browser Back key
    BROWSER_BACK((byte)166),
    //
    // Summary:
    //     Windows 2000/XP: Browser Forward key
    BROWSER_FORWARD((byte)167),
    //
    // Summary:
    //     Windows 2000/XP: Browser Refresh key
    BROWSER_REFRESH((byte)168),
    //
    // Summary:
    //     Windows 2000/XP: Browser Stop key
    BROWSER_STOP((byte)169),
    //
    // Summary:
    //     Windows 2000/XP: Browser Search key
    BROWSER_SEARCH((byte)170),
    //
    // Summary:
    //     Windows 2000/XP: Browser Favorites key
    BROWSER_FAVORITES((byte)171),
    //
    // Summary:
    //     Windows 2000/XP: Browser Start and Home key
    BROWSER_HOME((byte)172),
    //
    // Summary:
    //     Windows 2000/XP: Volume Mute key
    VOLUME_MUTE((byte)173),
    //
    // Summary:
    //     Windows 2000/XP: Volume Down key
    VOLUME_DOWN((byte)174),
    //
    // Summary:
    //     Windows 2000/XP: Volume Up key
    VOLUME_UP((byte)175),
    //
    // Summary:
    //     Windows 2000/XP: Next Track key
    MEDIA_NEXT_TRACK((byte)176),
    //
    // Summary:
    //     Windows 2000/XP: Previous Track key
    MEDIA_PREV_TRACK((byte)177),
    //
    // Summary:
    //     Windows 2000/XP: Stop Media key
    MEDIA_STOP((byte)178),
    //
    // Summary:
    //     Windows 2000/XP: Play/Pause Media key
    MEDIA_PLAY_PAUSE((byte)179),
    //
    // Summary:
    //     Windows 2000/XP: Start Mail key
    LAUNCH_MAIL((byte)180),
    //
    // Summary:
    //     Windows 2000/XP: Select Media key
    LAUNCH_MEDIA_SELECT((byte)181),
    //
    // Summary:
    //     Windows 2000/XP: Start Application 1 key
    LAUNCH_APP1((byte)182),
    //
    // Summary:
    //     Windows 2000/XP: Start Application 2 key
    LAUNCH_APP2((byte)183),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the ';:' key
    OEM_1((byte)186),
    //
    // Summary:
    //     Windows 2000/XP: For any country/region), the '+' key
    OEM_PLUS((byte)187),
    //
    // Summary:
    //     Windows 2000/XP: For any country/region), the '),' key
    OEM_COMMA((byte)188),
    //
    // Summary:
    //     Windows 2000/XP: For any country/region), the '-' key
    OEM_MINUS((byte)189),
    //
    // Summary:
    //     Windows 2000/XP: For any country/region), the '.' key
    OEM_PERIOD((byte)190),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the '/?' key
    OEM_2((byte)191),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the '`~' key
    OEM_3((byte)192),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the '[{' key
    OEM_4((byte)219),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the '\|' key
    OEM_5((byte)220),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the ']}' key
    OEM_6((byte)221),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard. Windows 2000/XP:
    //     For the US standard keyboard), the 'single-quote/double-quote' key
    OEM_7((byte)222),
    //
    // Summary:
    //     Used for miscellaneous characters; it can vary by keyboard.
    OEM_8((byte)223),
    //
    // Summary:
    //     Windows 2000/XP: Either the angle bracket key or the backslash key on the RT
    //     102-key keyboard
    OEM_102((byte)226),
    //
    // Summary:
    //     Windows 95/98/Me), Windows NT 4.0), Windows 2000/XP: IME PROCESS key
    PROCESSKEY((byte)229),
    //
    // Summary:
    //     Windows 2000/XP: Used to pass Unicode characters as if they were keystrokes.
    //     The PACKET key is the low word of a 32-bit Virtual Key value used for non-keyboard
    //     input methods. For more information), see Remark in KEYBDINPUT), SendInput), WM_KEYDOWN),
    //     and WM_KEYUP
    PACKET((byte)231),
    //
    // Summary:
    //     Attn key
    ATTN((byte)246),
    //
    // Summary:
    //     CrSel key
    CRSEL((byte)247),
    //
    // Summary:
    //     ExSel key
    EXSEL((byte)248),
    //
    // Summary:
    //     Erase EOF key
    EREOF((byte)249),
    //
    // Summary:
    //     Play key
    PLAY((byte)250),
    //
    // Summary:
    //     Zoom key
    ZOOM((byte)251),
    //
    // Summary:
    //     Reserved
    NONAME((byte)252),
    //
    // Summary:
    //     PA1 key
    PA1((byte)253),
    //
    // Summary:
    //     Clear key
    OEM_CLEAR((byte)254);

    public byte keyCode;
    WindowsKey(byte keyCode) {
        this.keyCode = keyCode;
    };
}
