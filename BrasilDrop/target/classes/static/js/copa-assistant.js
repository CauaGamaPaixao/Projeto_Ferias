class CopaAssistantChat {
    constructor() {
        this.messages = [];
        this.isLoading = false;
        this.launcher = document.querySelector('[data-copa-launcher]');
        this.panel = document.querySelector('[data-copa-panel]');
        this.closeButton = document.querySelector('[data-copa-close]');
        this.form = document.querySelector('[data-copa-form]');
        this.input = document.querySelector('[data-copa-input]');
        this.messagesContainer = document.querySelector('[data-copa-messages]');
        this.loading = document.querySelector('[data-copa-loading]');
        this.suggestions = document.querySelectorAll('[data-copa-suggestion]');
    }

    init() {
        if (!this.launcher || !this.panel || !this.form || !this.input || !this.messagesContainer || !this.loading) {
            return;
        }

        this.launcher.addEventListener('click', () => this.open());
        this.closeButton?.addEventListener('click', () => this.close());
        this.form.addEventListener('submit', (event) => {
            event.preventDefault();
            void this.sendMessage(this.input.value);
        });
        this.suggestions.forEach((button) => {
            button.addEventListener('click', () => {
                const suggestion = button.getAttribute('data-copa-suggestion') || button.textContent || '';
                void this.sendMessage(suggestion.trim());
            });
        });
    }

    open() {
        this.panel.removeAttribute('hidden');
        this.launcher.setAttribute('aria-expanded', 'true');
        window.setTimeout(() => this.input.focus(), 50);
    }

    close() {
        this.panel.setAttribute('hidden', 'hidden');
        this.launcher.setAttribute('aria-expanded', 'false');
    }

    async sendMessage(text) {
        const message = text.trim();
        if (!message || this.isLoading) {
            return;
        }

        this.addMessage('user', message);
        this.input.value = '';
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

            const data = await response.json();
            this.addMessage('assistant', data.response || 'Nao encontrei uma resposta agora.');
        } catch (error) {
            this.addMessage('assistant', 'Não foi possível conectar ao Copa Assistant no momento.');
        } finally {
            this.setLoading(false);
        }
    }

    addMessage(sender, text) {
        const chatMessage = { sender, text, timestamp: new Date() };
        this.messages.push(chatMessage);

        const item = document.createElement('div');
        item.className = `copa-message ${sender === 'user' ? 'copa-message-user' : 'copa-message-assistant'}`;
        item.innerHTML = `
            <p></p>
            <time>${chatMessage.timestamp.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' })}</time>
        `;
        item.querySelector('p').textContent = chatMessage.text;
        this.messagesContainer.appendChild(item);
        this.messagesContainer.scrollTop = this.messagesContainer.scrollHeight;
    }

    setLoading(isLoading) {
        this.isLoading = isLoading;
        this.loading.hidden = !isLoading;
        this.form.querySelector('button').disabled = isLoading;
        this.messagesContainer.scrollTop = this.messagesContainer.scrollHeight;
    }
}

document.addEventListener('DOMContentLoaded', () => new CopaAssistantChat().init());
