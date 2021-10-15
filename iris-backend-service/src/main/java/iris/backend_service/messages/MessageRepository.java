package iris.backend_service.messages;

import iris.backend_service.messages.Message.MessageIdentifier;

import org.springframework.data.jpa.repository.JpaRepository;

interface MessageRepository extends JpaRepository<Message, MessageIdentifier> {}
