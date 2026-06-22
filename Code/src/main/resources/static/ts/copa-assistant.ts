export interface ChatMessage {
    sender: 'user' | 'assistant';
    text: string;
    timestamp: Date;
}

type Sender = ChatMessage['sender'];

class CopaAssistantChat {
    private messages: ChatMessage[] = [];
    private isLoading = false;
    private readonly launcher = document.querySelector<HTMLButtonElement>('[data-copa-launcher]');
    private readonly panel = document.querySelector<HTMLElement>('[data-copa-panel]');
    private readonly closeButton = document.querySelector<HTMLButtonElement>('[data-copa-close]');
    private readonly form = document.querySelector<HTMLFormElement>('[data-copa-form]');
    private readonly input = document.querySelector<HTMLInputElement>('[data-copa-input]');
    private readonly messagesContainer = document.querySelector<HTMLElement>('[data-copa-messages]');
    private readonly loading = document.querySelector<HTMLElement>('[data-copa-loading]');
    private readonly suggestions = document.querySelectorAll<HTMLButtonElement>('[data-copa-suggestion]');

    init(): void {
        if (!this.launcher || !this.panel || !this.form || !this.input || !this.messagesContainer || !this.loading) {
            return;
        }

        this.launcher.addEventListener('click', () => this.open());
        this.closeButton?.addEventListener('click', () => this.close());
        this.form.addEventListener('submit', (event) => {
            event.preventDefault();
            void this.sendMessage(this.input?.value ?? '');
        });
        this.suggestions.forEach((button) => {
            button.addEventListener('click', () => {
                const suggestion = button.getAttribute('data-copa-suggestion') || button.textContent || '';
                void this.sendMessage(suggestion.trim());
            });
        });
    }

    private open(): void {
        this.panel?.removeAttribute('hidden');
        this.launcher?.setAttribute('aria-expanded', 'true');
        window.setTimeout(() => this.input?.focus(), 50);
    }

    private close(): void {
        this.panel?.setAttribute('hidden', 'hidden');
        this.launcher?.setAttribute('aria-expanded', 'false');
    }

    private async sendMessage(text: string): Promise<void> {
        const message = text.trim();
        if (!message || this.isLoading) {
            return;
        }

        this.addMessage('user', message);
        if (this.input) {
            this.input.value = '';
        }
        this.setLoading(true);

        try {
            const response = await fetch('/api/copa-assistant/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message })
            });

            if (!response.ok) {
                throw new Error('Copa Assistant unavailable');
            }

            const data = await response.json() as { response?: string };
            this.addMessage('assistant', data.response || 'Nao encontrei uma resposta agora.');
        } catch (error) {
            this.addMessage('assistant', 'Não foi possível conectar ao Copa Assistant no momento.');
        } finally {
            this.setLoading(false);
        }
    }

    private addMessage(sender: Sender, text: string): void {
        if (!this.messagesContainer) {
            return;
        }

        const chatMessage: ChatMessage = { sender, text, timestamp: new Date() };
        this.messages.push(chatMessage);

        const item = document.createElement('div');
        item.className = `copa-message ${sender === 'user' ? 'copa-message-user' : 'copa-message-assistant'}`;
        item.innerHTML = `
            <p></p>
            <time>${chatMessage.timestamp.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })}</time>
        `;
        item.querySelector('p')!.textContent = chatMessage.text;
        this.messagesContainer.appendChild(item);
        this.messagesContainer.scrollTop = this.messagesContainer.scrollHeight;
    }

    private setLoading(isLoading: boolean): void {
        this.isLoading = isLoading;
        if (this.loading) {
            this.loading.hidden = !isLoading;
        }
        const submitButton = this.form?.querySelector<HTMLButtonElement>('button');
        if (submitButton) {
            submitButton.disabled = isLoading;
        }
        if (this.messagesContainer) {
            this.messagesContainer.scrollTop = this.messagesContainer.scrollHeight;
        }
    }
}

document.addEventListener('DOMContentLoaded', () => new CopaAssistantChat().init());
