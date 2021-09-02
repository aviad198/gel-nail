import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';
import { applyTheme } from 'Frontend/generated/theme';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators';

@customElement('nail-salons-view')
export class NailSalonsView extends LitElement {
  connectedCallback() {
    super.connectedCallback();
    // Apply the theme manually because of https://github.com/vaadin/flow/issues/11160
    applyTheme(this.renderRoot);
  }

  render() {
    return html`
      <main class="max-w-screen-lg mx-auto pb-l px-l">
        <vaadin-horizontal-layout class="items-center justify-between">
          <vaadin-vertical-layout>
            <h2 class="mb-0 mt-xl text-3xl">Find a nail salon</h2>
            <p class="mb-xl mt-0 text-secondary">find the best place to take care of your nails</p>
          </vaadin-vertical-layout>
          <vaadin-select label="Sort by" id="sortBy"></vaadin-select>
        </vaadin-horizontal-layout>
        <ol class="gap-m grid list-none m-0 p-0">
          <slot></slot>
        </ol>
      </main>
    `;
  }
}
