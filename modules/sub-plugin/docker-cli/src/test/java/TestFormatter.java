/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Code Technology Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
import com.github.dockerjava.core.NameParser;
import org.junit.Test;

import java.util.Formatter;

/**
 * @author bwcx_jzy
 * @since 2022/2/7
 */
public class TestFormatter {

    @Test
    public void test() {
        System.out.println(String.format("${a}", "1"));
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("${a}", "1"));
    }

    @Test
    public void testTag() {
        NameParser.ReposTag reposTag = NameParser.parseRepositoryTag("192.168.33.106:10087/library/sso:3.0.0.RELEASE");
        System.out.println(reposTag);

        reposTag = NameParser.parseRepositoryTag("sso:3.0.0.RELEASE");
        System.out.println(reposTag);

        reposTag = NameParser.parseRepositoryTag("sso");
        System.out.println(reposTag);
    }
}
