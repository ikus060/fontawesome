/**
 * Copyright (c) 2015 Patrik Dufresne Service Logiciel <info@patrikdufresne.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Patrik Dufresne - initial API and implementation
 */
package com.patrikdufresne.fontawesome;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class FontAwesomeTest {

    public static void main(final String[] args) throws IllegalArgumentException, IllegalAccessException {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setText("FontAwesome Snippet");
        shell.setSize(600, 600);
        shell.setLayout(new GridLayout(24, false));

        // Loop on each COLOR constants.
        Field[] fields = FontAwesome.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.getType().equals(String.class)) {
                continue;
            }
            String value = (String) field.get(null);
            if (value.length() != 1) {
                continue;
            }
            Label text = new Label(shell, SWT.NONE);
            text.setFont(FontAwesome.getFont(22));
            text.setText(value);
            text.setToolTipText(field.getName());
        }

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();

    }

}
