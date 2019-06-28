import WidgetsApp from '../../src/widgets/WidgetsApp';
import { Session, NullWidget } from '@eclipse-scout/eclipse-scout';
import WidgetsDesktop from '../../src/widgets/WidgetsDesktop';

describe('WidgetsDesktop', () => {
  it('init', () => {
    var desktop = new WidgetsDesktop();
    var nullWidget = new NullWidget();
    nullWidget.session = new Session();
    desktop.init({
      parent: nullWidget
    });
    expect(desktop.viewButtons.length).toBe(3);
  });
});
