/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title CTResult.java
 * @Package com.probestar.configurationtools
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 31, 2015 2:24:56 PM
 * @version V1.0
 * @Description
 */

package com.probestar.configurationtools;

public class CTResult {
    private CTResultType _type;
    private String _context;

    public CTResult(String context) {
        this(CTResultType.Print, context);
    }

    public CTResult(CTResultType type, String context) {
        _type = type;
        _context = context;
    }

    public CTResultType getType() {
        return _type;
    }

    public String getContext() {
        return _context;
    }

    public void appendContext(String context) {
        _context += context;
    }
}
