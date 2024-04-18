const axios = require('axios');

const apiKey = "";

//chatbot in javascript

async function chatWithGPT(prompt) {
    try {
        const response = await axios.post('https://api.openai.com/v1/chat/completions', {
            model: "text-davinci-002",
            messages: [{ role: "user", content: prompt }]
        }, {
            headers: {
                'Authorization': `Bearer ${apiKey}`,
                'Content-Type': 'application/json'
            }
        });

        return response.data.choices[0].message.content.trim();
    } catch (error) {
        console.error('Error:', error.response.data.error.message);
        return null;
    }
}

async function main() {
    while (true) {
        const user_input = prompt("You: ");
        if (user_input.toLowerCase() === "quit" || user_input.toLowerCase() === "exit" || user_input.toLowerCase() === "bye") {
            break;
        }

        const response = await chatWithGPT(user_input);
        console.log("Chatbot:", response);
    }
}

main();
