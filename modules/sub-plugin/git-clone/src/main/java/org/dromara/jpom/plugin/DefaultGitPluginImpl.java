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
package org.dromara.jpom.plugin;

import cn.keepbx.jpom.plugins.PluginConfig;
import org.eclipse.jgit.api.Git;

import java.util.Map;

/**
 * @author bwcx_jzy
 * @since 2022/2/22
 */
@PluginConfig(name = "git-clone")
public class DefaultGitPluginImpl implements IWorkspaceEnvPlugin {

    @Override
    public Object execute(Object main, Map<String, Object> parameter) throws Exception {
        String type = main.toString();
        GitProcess gitProcess = GitProcessFactory.get(parameter, this);
        switch (type) {
            case "branchAndTagList":
                return gitProcess.branchAndTagList();
            case "pull": {
                return gitProcess.pull();
            }
            case "pullByTag": {
                return gitProcess.pullByTag();
            }
            case "systemGit": {
                return GitProcessFactory.existsSystemGit();
            }
            default:
                break;
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        Git.shutdown();
    }
}
