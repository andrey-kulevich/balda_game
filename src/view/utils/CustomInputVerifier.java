package view.utils;

import javax.swing.*;

public class CustomInputVerifier extends InputVerifier {

    private int _maxLength;

    public CustomInputVerifier(int maxLength) {
        super();
        if (maxLength < 1) throw new IllegalArgumentException("Max length of input cannot be less than 1");
        _maxLength = maxLength;
    }

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        int length = ((JTextField) input).getText().length();
        if (length >= _maxLength) ((JTextField) input).setText(text.substring(0, _maxLength));
        return length <= _maxLength && length > 0;
    }

//    @Override
//    public boolean verifyTarget(JComponent input) {
//        int length = ((JTextField) input).getText().length();
//        return length <= _maxLength && length > 0;
//    }
//
//    @Override
//    public boolean shouldYieldFocus(JComponent source,
//                                    JComponent target) {
//        return verify(source) || verifyTarget(target);
//    }
}
