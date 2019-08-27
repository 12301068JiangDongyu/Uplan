import { SmartCPage } from './app.po';

describe('smart-c App', () => {
  let page: SmartCPage;

  beforeEach(() => {
    page = new SmartCPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
