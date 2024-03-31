#Chatbot.py- Hima Nagi Reddy

#This use case will help the user interact with the AI Chatbot

#Test Case 1: The chatbot will reply to let user know about invalid input 
#Execution: User manually enters invalid input into the AI chatbot 

#Test Case 2: The chatbot will end the conversation when saying specific words.
#Execution: The chatbot will end the conversation when the user manually enters "quit", "exit", or "bye".








from openai import OpenAI

api_key = ""
client = OpenAI(api_key=api_key)


def chat_with_gpt(prompt):
    response = client.chat.completions.create(
        model="gpt-3.5-turbo", messages=[{"role": "user", "content": prompt}]
    )

    return response.choices[0].message.content.strip()


if __name__ == "__main__":
    while True:
        user_input = input("You: ")
        if user_input.lower() in ["quit", "exit", "bye"]:
            break

        response = chat_with_gpt(user_input)
        print("Chatbot:", response)
