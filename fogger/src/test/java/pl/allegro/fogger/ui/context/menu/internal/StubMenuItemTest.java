/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.allegro.fogger.ui.context.menu.internal;

import android.content.Intent;

import com.googlecode.catchexception.CatchException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import pl.allegro.fogger.utils.ClosureImitation;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class StubMenuItemTest {

    private StubMenuItem stubMenuItem;

    @Before
    public void setUp() {
        stubMenuItem = new StubMenuItem(RuntimeEnvironment.application);
    }

    @Test
    public void shouldSetMenuItemId() {
        //given
        int itemId = 12;

        //when
        stubMenuItem.setItemId(itemId);

        //then
        assertThat(stubMenuItem.getItemId()).isEqualTo(itemId);
    }

    @Test
    public void shouldCreateWithItemId() {
        //given
        int itemId = 12;

        //when
        stubMenuItem = new StubMenuItem(itemId);

        //then
        assertThat(stubMenuItem.getItemId()).isEqualTo(itemId);
    }

    @Test
    public void shouldSetIntent() {
        //given
        Intent intent = new Intent();

        //when
        stubMenuItem.setIntent(intent);

        //then
        assertThat(stubMenuItem.getIntent()).isEqualTo(intent);
    }

    @Test
    public void shouldSetCheckable() {
        //when
        stubMenuItem.setCheckable(true);

        //then
        assertThat(stubMenuItem.isCheckable()).isTrue();
    }

    @Test
    public void shouldSetChecked() {
        //when
        stubMenuItem.setChecked(true);

        //then
        assertThat(stubMenuItem.isChecked()).isTrue();
    }

    @Test
    public void shouldSetEnabled() {
        //when
        stubMenuItem.setEnabled(true);

        //then
        assertThat(stubMenuItem.isEnabled()).isTrue();
    }

    @Test
    public void shouldSetVisible() {
        //when
        stubMenuItem.setVisible(true);

        //then
        assertThat(stubMenuItem.isVisible()).isTrue();
    }

    @Test
    public void shouldThrowUnsupportedException() {
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setOnMenuItemClickListener(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setActionView(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getActionView();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setShowAsAction(1);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getSubMenu();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.hasSubMenu();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setActionProvider(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getActionProvider();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.expandActionView();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.collapseActionView();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.isActionViewExpanded();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setOnActionExpandListener(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getTitleCondensed();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setIcon(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getIcon();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setShortcut('a', 'a');
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getNumericShortcut();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getAlphabeticShortcut();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setShowAsActionFlags(24);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.getMenuInfo();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation() {
            @Override
            public void call() {
                stubMenuItem.setActionView(43);
            }
        });
    }


    public void verifyIfIsUnsupported(ClosureImitation closure) {
        catchException(closure).call();

        //then
        assertThat(CatchException.<Exception>caughtException()).isInstanceOf(UnsupportedOperationException.class);
    }
}