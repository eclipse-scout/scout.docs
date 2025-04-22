/*
 * Copyright (c) BSI Business Systems Integration AG. All rights reserved.
 * http://www.bsiag.com/
 */
import {JasmineScoutUtil} from '@eclipse-scout/core/testing';
import '../../../main/js/index'; // Ensure all pages from this module are included

describe('PageCompleteness', () => {
  it('all pages have a uuid and a pageParam', () => {
    JasmineScoutUtil.assertPageCompleteness({
      namespace: 'jswidgets',
      assertPageParam: false
    });
  });
});
