# Security Policy

## Supported Versions

| Version | Supported |
|---------|-----------|
| 0.1.x   | Yes       |

## Reporting a Vulnerability

**Do not open a public issue for security vulnerabilities.**

Instead, please report security issues by emailing the maintainers directly or by opening a private security advisory on GitHub.

### What to include

- Description of the vulnerability
- Steps to reproduce
- Impact assessment
- Suggested fix (if any)

### Response timeline

- We will acknowledge your report within 48 hours.
- We will provide a fix or mitigation plan within 7 days.
- We will credit you in the release notes (unless you prefer to stay anonymous).

## Security Best Practices

When using PageIndex:

- **LLM API keys**: Never hardcode API keys. Use environment variables or a secrets manager.
- **Document content**: Be aware that document text is sent to your LLM provider. Do not index sensitive documents with external LLM APIs unless your provider agreement allows it.
- **Storage**: The default `InMemoryDocumentTreeStore` does not persist data. For production, implement `DocumentTreeStore` with proper access controls.
